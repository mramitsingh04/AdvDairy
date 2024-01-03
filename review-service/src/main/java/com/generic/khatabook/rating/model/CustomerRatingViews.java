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
public class CustomerRatingViews {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final CustomerView customer;
    private final long totalNumberRating;
    private final double minRating;
    private final double maxRating;
    private final double avgRating;
    private final List<ProductView> productRatings;

    public CustomerRatingViews(final List<ProductView> productRatings) {

        this(null, productRatings);

    }

    public CustomerRatingViews(CustomerView customer, final List<ProductView> productRatings) {
        this.productRatings = productRatings;
        this.customer = customer;
        DoubleSummaryStatistics statistics = productRatings.stream().map(ProductView::rating).mapToDouble(Float::doubleValue).summaryStatistics();
        this.totalNumberRating = statistics.getCount();
        this.minRating = Math.floor(statistics.getMin());
        this.maxRating = Math.ceil(statistics.getMax());
        this.avgRating = Math.round(statistics.getAverage());
    }

    public CustomerRatingViews(final CustomerView customerView) {

        this(customerView, Collections.emptyList());

    }


}
