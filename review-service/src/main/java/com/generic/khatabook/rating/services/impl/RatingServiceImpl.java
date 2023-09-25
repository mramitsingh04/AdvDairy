package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.entity.Rating;
import com.generic.khatabook.rating.repository.RatingRepository;
import com.generic.khatabook.rating.services.RatingService;
import com.generic.khatabook.rating.services.mapper.RatingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository myRatingRepository;
    @Autowired
    private RatingMapper ratingMapper;

/*
    @Autowired
    public RatingServiceImpl(final RatingRepository thatRatingRepository, final RatingMapper thatRatingMapper)
    {
        this.myRatingRepository = thatRatingRepository;
        this.ratingMapper = thatRatingMapper;
    }
*/

    @Override
    public List<RatingDTO> findProductRatingByProductId(final String productId) {
        return ratingMapper.mapToDTOs(myRatingRepository.findByProductId(productId));
    }

    @Override
    public List<RatingDTO> findProductRatingByCustomerId(final String customerId) {
        return ratingMapper.mapToDTOs(myRatingRepository.findByCustomerId(customerId));
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
        return ratingMapper.mapToDTO(myRatingRepository.findByCustomerIdAndProductId(customerId, productId));
    }

    @Override
    public RatingDTO updateRating(final ProductDTO productDetails, final RatingDTO customerUpdateRating) {

        if (Objects.nonNull(productDetails)) {
            Rating productRating = myRatingRepository.findByCustomerIdAndProductId(customerUpdateRating.customerId(), customerUpdateRating.productId());

            if (Objects.nonNull(productRating)) {
                productRating.setDescription(customerUpdateRating.description());
                productRating.setRating(customerUpdateRating.rating());
                myRatingRepository.save(productRating);
                return ratingMapper.mapToDTO(productRating);
            } else {
                return saveRating(customerUpdateRating);
            }
        }
        return null;

    }
}
