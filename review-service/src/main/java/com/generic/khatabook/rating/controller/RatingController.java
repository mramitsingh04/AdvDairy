package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.exceptions.AppEntity;
import com.generic.khatabook.rating.exceptions.LimitBonusException;
import com.generic.khatabook.rating.exceptions.NotFoundException;
import com.generic.khatabook.rating.exchanger.CustomerClient;
import com.generic.khatabook.rating.exchanger.ProductClient;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingViews;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;
import com.generic.khatabook.rating.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.Objects.isNull;

@RestController()
@RequestMapping("/rating-service")
@Slf4j
public class RatingController {

    private RatingService myRatingService;
    private ProductClient myProductClient;
    private CustomerClient myCustomerClient;

    @Autowired
    public RatingController(final RatingService ratingService, final ProductClient myProductClient, final CustomerClient myCustomerClient) {
        this.myRatingService = ratingService;
        this.myProductClient = myProductClient;
        this.myCustomerClient = myCustomerClient;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRating(@RequestBody RatingDTO rating)
    {
        if (isNull(rating) || isNull(rating.entityId())) {
            return ResponseEntity.noContent().build();
        }

        final ProductDTO productDetails = myProductClient.getProductById(rating.entityId()).getBody();
        if (isNull(productDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, rating.entityId()).get()).build();
        }
        if (rating.rating() > 5) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MAX, rating.rating()).get()).build();
        } else if (rating.rating() < 0) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MIN, rating.rating()).get()).build();
        }
        final RatingDTO ratingDTO = myRatingService.updateRating(productDetails, rating);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{customerId}/product/{entityId}").buildAndExpand(ratingDTO.fromCustomerId(), ratingDTO.entityId()).toUri()).build();
    }

    @GetMapping("/product/{entityId}")
    public ResponseEntity<?> findAllRatingByProductId(@PathVariable String productId) {
        ProductView productView = null;
        ProductDTO productDTO = null;
        try {
            ResponseEntity<ProductDTO> productDetails = myProductClient.getProductById(productId);
            productDTO = productDetails.getBody();
            productView = new ProductView(productDTO.id(), productDTO.name());

        } catch (WebClientRequestException e) {
            final ProblemDetail prodNotFound = new NotFoundException(AppEntity.PRODUCT, productId).get();
            log.error(prodNotFound.getDetail(), e);
            return ResponseEntity.of(prodNotFound).build();
        }

        final List<RatingDTO> productRatings = myRatingService.findProductRatingByProductId(productDTO.id());
        if (isNull(productRatings) || productRatings.isEmpty()) {
            return ResponseEntity.ok(new ProductRatingViews(productView));
        }
        return ResponseEntity.ok(new ProductRatingViews(productView, productRatings));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId) {
        ProductView productView = null;
        final List<RatingDTO> productRatings = myRatingService.findProductRatingByCustomerId(customerId);
        if (isNull(productRatings) || productRatings.isEmpty()) {
            return ResponseEntity.ok(new ProductRatingViews(productView));
        }
        return ResponseEntity.ok(new ProductRatingViews(productRatings));
    }

    @GetMapping("/customer/{customerId}/product/{entityId}")
    public ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId, @PathVariable String productId) {
        final RatingDTO productRatings = myRatingService.findProductRatingByCustomerIdAndProductId(customerId, productId);

        return ResponseEntity.ok(productRatings);
    }

}
