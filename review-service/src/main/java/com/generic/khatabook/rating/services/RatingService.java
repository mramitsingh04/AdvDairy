package com.generic.khatabook.rating.services;


import com.generic.khatabook.rating.model.CustomerRatingViews;
import com.generic.khatabook.rating.model.CustomerView;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingViews;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;

import java.util.List;

public interface RatingService {
    public CustomerView convertToCustomerView(final RatingDTO ratingDTO);
    public ProductView convertToProductView(final RatingDTO ratingDTO);
    List<RatingDTO> findAllRating();
    ProductRatingViews findProductRatingByProductId(String productId);
    Float getRatingByEntityId(String productId);
    List<RatingDTO> findRatingByEntityTypeAndEntityId(RatingEntityType entityType, String productId);

    CustomerRatingViews findProductRatingByCustomerId(String customerId);

    RatingDTO saveRating(final RatingDTO ratingDTO);

    RatingDTO findProductRatingByCustomerIdAndProductId(String customerId, String productId);

    RatingDTO updateRating(ProductDTO productDetails, RatingDTO customerUpdateRating);
}
