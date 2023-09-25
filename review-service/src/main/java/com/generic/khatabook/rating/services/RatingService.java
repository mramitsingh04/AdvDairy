package com.generic.khatabook.rating.services;


import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.RatingDTO;

import java.util.List;

public interface RatingService {

    List<RatingDTO> findProductRatingByProductId(String productId);

    List<RatingDTO> findProductRatingByCustomerId(String customerId);

    RatingDTO saveRating(final RatingDTO ratingDTO);

    RatingDTO findProductRatingByCustomerIdAndProductId(String customerId, String productId);

    RatingDTO updateRating(ProductDTO productDetails, RatingDTO customerUpdateRating);
}
