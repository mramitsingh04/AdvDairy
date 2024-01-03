package com.generic.khatabook.product.services.impl;

import com.generic.khatabook.product.exchanger.RatingClient;
import com.generic.khatabook.product.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingClient myRatingClient;

    @Override
    public float fetchProductRating(final String productId) {

        log.info("Request for product rating{}", productId);

        final ResponseEntity<Float> reviewClient;
        try {
            reviewClient = myRatingClient.getRatingForProductId(productId);
            if (isNull(reviewClient) || isNull(reviewClient.getBody())) {
                log.info("Default product rating {} {}", productId, 0f);
                return 0f;
            }
            return reviewClient.getBody();
        } catch (Exception e) {
            log.error("Default product rating {} {}", productId, 0f);
            return 0f;
        }

    }
}
