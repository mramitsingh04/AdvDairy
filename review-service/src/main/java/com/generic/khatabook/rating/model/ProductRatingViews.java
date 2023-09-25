package com.generic.khatabook.rating.model;

import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Data
@ToString
public class ProductRatingViews {
    private final ProductView product;
    private final long totalNumberRating;
    private final double minRating;
    private final double maxRating;
    private final double avgRating;
    private final List<com.generic.khatabook.rating.model.RatingDTO> productRatings;

    public ProductRatingViews(final List<com.generic.khatabook.rating.model.RatingDTO> productRatings) {

        this(null, productRatings);

    }

    public ProductRatingViews(ProductView product, final List<com.generic.khatabook.rating.model.RatingDTO> productRatings) {
        this.productRatings = productRatings;
        this.product = product;
        DoubleSummaryStatistics statistics = productRatings.stream().map(RatingDTO::rating).mapToDouble(Float::doubleValue).summaryStatistics();
        this.totalNumberRating = statistics.getCount();
        this.minRating = statistics.getMin();
        this.maxRating = statistics.getMax();
        this.avgRating = Math.round(statistics.getAverage());
    }

    public ProductRatingViews(final ProductView productId) {

        this(productId, Collections.emptyList());

    }


}
