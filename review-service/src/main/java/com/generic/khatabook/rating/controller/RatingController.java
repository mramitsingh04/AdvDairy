package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.exceptions.AppEntity;
import com.generic.khatabook.rating.exceptions.LimitBonusException;
import com.generic.khatabook.rating.exceptions.NotFoundException;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingViews;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.exchanger.ProductClient;
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
import java.util.Objects;

@RestController()
@RequestMapping("/rating-service")
@Slf4j
public class RatingController {

    private RatingService myRatingService;
    private ProductClient myProductClient;

    @Autowired
    public RatingController(final RatingService ratingService, final ProductClient myProductClient) {
        this.myRatingService = ratingService;
        this.myProductClient = myProductClient;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRating(@RequestBody RatingDTO rating)
    {
        final ProductDTO productDetails = myProductClient.getProductById(rating.productId()).getBody();
        if (Objects.isNull(productDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, rating.productId()).get()).build();
        }
        if (rating.rating() > 5) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MAX, rating.rating()).get()).build();
        } else if (rating.rating() < 0) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MIN, rating.rating()).get()).build();
        }
        final RatingDTO ratingDTO = myRatingService.updateRating(productDetails, rating);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{customerId}/product/{productId}").buildAndExpand(ratingDTO.customerId(), ratingDTO.productId()).toUri()).build();
    }

    @GetMapping("/product/{productId}")
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
        if (Objects.isNull(productRatings) || productRatings.isEmpty()) {
            return ResponseEntity.ok(new ProductRatingViews(productView));
        }
        return ResponseEntity.ok(new ProductRatingViews(productView, productRatings));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId) {
        ProductView productView = null;
        final List<RatingDTO> productRatings = myRatingService.findProductRatingByCustomerId(customerId);
        if (Objects.isNull(productRatings) || productRatings.isEmpty()) {
            return ResponseEntity.ok(new ProductRatingViews(productView));
        }
        return ResponseEntity.ok(new ProductRatingViews(productRatings));
    }

    @GetMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<?> findAllRatingByCustomerId(@PathVariable String customerId, @PathVariable String productId) {
        final RatingDTO productRatings = myRatingService.findProductRatingByCustomerIdAndProductId(customerId, productId);

        return ResponseEntity.ok(productRatings);
    }

}
