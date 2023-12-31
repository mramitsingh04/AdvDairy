package com.generic.khatabook.rating.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRatingViews {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ProductView product;
    private final long totalNumberRating;
    private final double minRating;
    private final double maxRating;
    private final double avgRating;
    private final List<CustomerView> customerRatings;

    public ProductRatingViews(final List<CustomerView> customerRatings) {

        this(null, customerRatings);

    }

    public ProductRatingViews(ProductView product, final List<CustomerView> customerRatings) {
        this.customerRatings = customerRatings;
        this.product = product;
        DoubleSummaryStatistics statistics = customerRatings.stream().map(CustomerView::rating).mapToDouble(Float::doubleValue).summaryStatistics();
        this.totalNumberRating = statistics.getCount();
        this.minRating = Math.floor(statistics.getMin());
        this.maxRating = Math.ceil(statistics.getMax());
        this.avgRating = Math.round(statistics.getAverage());
    }

    public ProductRatingViews(final ProductView productId) {

        this(productId, Collections.emptyList());

    }

    public double getAvgRating() {
        return avgRating;
    }
}
