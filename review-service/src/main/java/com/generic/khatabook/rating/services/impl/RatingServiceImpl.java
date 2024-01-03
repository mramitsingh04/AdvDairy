package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.rating.entity.Rating;
import com.generic.khatabook.rating.model.CustomerRatingViews;
import com.generic.khatabook.rating.model.CustomerView;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingViews;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.RatingEntityType;
import com.generic.khatabook.rating.repository.RatingRepository;
import com.generic.khatabook.rating.services.CustomerProxyService;
import com.generic.khatabook.rating.services.ProductProxyService;
import com.generic.khatabook.rating.services.RatingService;
import com.generic.khatabook.rating.services.mapper.RatingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository myRatingRepository;
    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private CustomerProxyService myCustomerProxyService;
    @Autowired
    private ProductProxyService myProductProxyService;

    @Override
    public Float getRatingByEntityId(final String productId) {
        final List<RatingDTO> ratingDTOS = ratingMapper.mapToDTOs(myRatingRepository.findByEntityId(productId));
        DoubleSummaryStatistics statistics =
                ratingDTOS.stream().map(RatingDTO::rating).mapToDouble(Float::doubleValue).summaryStatistics();
        return (float) Math.round(statistics.getAverage());
    }


    @Override
    public List<RatingDTO> findAllRating() {
        return ratingMapper.mapToDTOs(myRatingRepository.findAll(Sort.by(Sort.Direction.ASC, "entityId")));
    }

    @Override
    public ProductRatingViews findProductRatingByProductId(final String productId) {
        final ProductView productView = myProductProxyService.getProductById(productId);
        final List<RatingDTO> ratingDTOS = ratingMapper.mapToDTOs(myRatingRepository.findByEntityId(productId));
        return new ProductRatingViews(productView, ratingDTOS.stream().map(this::convertToCustomerView).collect(Collectors.toList()));
    }

    @Override
    public List<RatingDTO> findRatingByEntityTypeAndEntityId(final RatingEntityType entityType, final String entityId) {


        return allReview();
    }

    @Override
    public CustomerRatingViews findProductRatingByCustomerId(final String customerId) {

        final CustomerView customer = myCustomerProxyService.getCustomerById(customerId);

        return new CustomerRatingViews(customer,
                ratingMapper.mapToDTOs(myRatingRepository.findByCustomerId(customerId)).stream().map(this::convertToProductView).collect(Collectors.toList()));
    }

    @Override
    public RatingDTO saveRating(final RatingDTO ratingDTO) {
        log.info(ratingDTO + " saving.");
        final RatingDTO persistRatingDTO = ratingMapper.mapToDTO(myRatingRepository.save(ratingMapper.mapToEntity(ratingDTO)));

        log.info(persistRatingDTO + " saved.");
        return persistRatingDTO;


    }

    @Override
    public RatingDTO findProductRatingByCustomerIdAndProductId(final String customerId, final String productId) {
        return ratingMapper.mapToDTO(myRatingRepository.findByCustomerIdAndEntityId(customerId, productId));
    }

    @Override
    public RatingDTO updateRating(final ProductDTO productDetails, final RatingDTO customerUpdateRating) {

        if (Objects.nonNull(productDetails)) {
            Rating productRating = myRatingRepository.findByCustomerIdAndEntityId(customerUpdateRating.customerId(), customerUpdateRating.entityId());

            if (Objects.nonNull(productRating)) {
                productRating.setDescription(customerUpdateRating.description());
                productRating.setRating(customerUpdateRating.rating());
                myRatingRepository.save(productRating);
                return ratingMapper.mapToDTO(productRating);
            } else {
                return saveRating(customerUpdateRating);
            }
        }
        return saveRating(customerUpdateRating);

    }

    public CustomerView convertToCustomerView(final RatingDTO ratingDTO) {

        return CustomerView.of(ratingDTO.customerId(), null, ratingDTO.rating(), ratingDTO.description());
    }

    public ProductView convertToProductView(final RatingDTO ratingDTO) {
        final ProductView product = myProductProxyService.getProductById(ratingDTO.entityId());

        return ProductView.of(product.id(), product.name(), ratingDTO.rating());
    }

    private List<RatingDTO> allReview() {
        return findAllRating();
    }

/*
    private List<RatingDTO> customerReview(final String entityId) {
        return findProductRatingByCustomerId(entityId);
    }
*/

 /*   private List<RatingDTO> specificationReview(final String entityId) {
        return findProductRatingByProductId(entityId);
    }

    private List<RatingDTO> productReview(final String entityId) {
        return findProductRatingByProductId(entityId);
    }*/
}
