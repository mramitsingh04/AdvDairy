package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.exceptions.AppEntity;
import com.generic.khatabook.rating.exceptions.LimitBonusException;
import com.generic.khatabook.rating.exchanger.CustomerClient;
import com.generic.khatabook.rating.model.CustomerRatingViews;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingViews;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;
import com.generic.khatabook.rating.services.ProductProxyService;
import com.generic.khatabook.rating.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController()
@RequestMapping("/rating-service")
@Slf4j
public class RatingController {

    private RatingService myRatingService;


    @Autowired
    public RatingController(final RatingService ratingService) {
        this.myRatingService = ratingService;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRating(@RequestBody RatingDTO rating)
    {
        if (isNull(rating) || isNull(rating.entityId())) {
            return ResponseEntity.noContent().build();
        }

//        final ProductDTO productDetails = myProductClient.getProductById(rating.entityId()).getBody();
        final ProductDTO productDetails = null;
//        if (isNull(productDetails)) {
//            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, rating.entityId()).get()).build();
//        }
        if (rating.rating() > 5) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MAX, rating.rating()).get()).build();
        } else if (rating.rating() < 0) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MIN, rating.rating()).get()).build();
        }
        final RatingDTO ratingDTO = myRatingService.updateRating(productDetails, rating);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{customerId}/product/{entityId}").buildAndExpand(ratingDTO.customerId(), ratingDTO.entityId()).toUri()).build();
    }

    @GetMapping("/product/{entityId}")
    public ResponseEntity<?> findAllRatingByProductId(@PathVariable String entityId) {
        final ProductRatingViews productRatings = myRatingService.findProductRatingByProductId(entityId);
        if (isNull(productRatings)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productRatings);
    }
    @GetMapping("/product/{entityId}/lite")
    public ResponseEntity<Float> getRatingForProductId(@PathVariable String entityId) {
        final Float productRatings = myRatingService.getRatingByEntityId(entityId);
        if (isNull(productRatings)) {
            return ResponseEntity.of(Optional.of(0f));
        }
        return ResponseEntity.ok(productRatings);
    }

    @GetMapping("/{entityType}/{entityId}")
    public ResponseEntity<?> findRatingByEntityTypeAndEntityId(@PathVariable String entityType, @PathVariable String entityId)
    {
        if (entityType == null || entityId == null) {
            return ResponseEntity.ok(new ProductRatingViews( myRatingService.findAllRating().stream().map(myRatingService::convertToCustomerView).collect(Collectors.toList())));
        }

        RatingEntityType ratingEntityType = RatingEntityType.NONE;
        try {
            ratingEntityType = RatingEntityType.valueOf(entityType);
        } catch (IllegalArgumentException e) {
            log.error("Illegal Argument Exception {}", entityType);
        }

        final List<RatingDTO> productRatings = myRatingService.findRatingByEntityTypeAndEntityId(ratingEntityType, entityId);
        final ProductView productView = ProductView.of(entityId);
        if (isNull(productRatings) || productRatings.isEmpty()) {
            return ResponseEntity.ok(new ProductRatingViews(productView));
        }
        return ResponseEntity.ok(new ProductRatingViews(productView, productRatings.stream().map(myRatingService::convertToCustomerView).collect(Collectors.toList())));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerRatingViews> findAllRatingByCustomerId(@PathVariable String customerId) {
        final CustomerRatingViews customerRatingViews = myRatingService.findProductRatingByCustomerId(customerId);
        if (isNull(customerRatingViews)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerRatingViews);
    }

    @GetMapping("/customer/{customerId}/product/{entityId}")
    public ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId, @PathVariable String entityId) {
        final RatingDTO productRatings = myRatingService.findProductRatingByCustomerIdAndProductId(customerId, entityId);

        return ResponseEntity.ok(productRatings);
    }

}
