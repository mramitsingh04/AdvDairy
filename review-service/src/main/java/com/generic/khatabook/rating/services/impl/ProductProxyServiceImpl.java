package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.rating.exceptions.AppEntity;
import com.generic.khatabook.rating.exceptions.NotFoundException;
import com.generic.khatabook.rating.exchanger.ProductClient;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductView;
import com.generic.khatabook.rating.services.ProductProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductProxyServiceImpl implements ProductProxyService {


    @Autowired
    private ProductClient myProductClient;

    @Override
    public ProductView getProductById(final String productId) {
        ProductView productView = null;
        try {
            ResponseEntity<ProductDTO> productDetails = myProductClient.getProductById(productId);
            ProductDTO productDTO = productDetails.getBody();
            return ProductView.of(productDTO.id(), productDTO.name());

        } catch (WebClientRequestException e) {
            final ProblemDetail prodNotFound = new NotFoundException(AppEntity.PRODUCT, productId).get();
            log.error(prodNotFound.getDetail(), e);
        }
        return ProductView.of(productId);
    }
}
