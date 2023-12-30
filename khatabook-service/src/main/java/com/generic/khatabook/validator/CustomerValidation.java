package com.generic.khatabook.validator;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.exchanger.SpecificationClient;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.Product;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.KhatabookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
@Component
public class CustomerValidation {
    List<ProblemDetail> errors = new ArrayList<>();
    @Autowired
    private SpecificationClient mySpecificationClient;
    @Autowired
    private ProductClient myProductClient;
    @Autowired
    private CustomerService myCustomerService;
    @Autowired
    private KhatabookService myKhatabookService;


    public ProblemDetail doCustomerProductValidation(final Product product) {
        String productId = product.productId();
        try {
            final ResponseEntity<?> responseEntity = myProductClient.getProductById(productId);
            if (Objects.isNull(responseEntity)) {
                return new NotFoundException(AppEntity.PRODUCT, productId).get();
            }
        } catch (WebClientResponseException e) {
            return new NotFoundException(AppEntity.PRODUCT, productId).get();
        }
        return null;
    }

    public ProblemDetail doCustomerProductValidation(final List<Product> products) {
        for (Product product : products) {
            ProblemDetail problemDetail = doCustomerProductValidation(product);
            if (problemDetail != null) {
                return problemDetail;
            }
        }
        return null;
    }

    public List<ProblemDetail> createValidation(CustomerDTO customerDTO) {
        final ProblemDetail khatabookValidation = doKhatabookValidation(customerDTO.khatabookId());
        if (Objects.nonNull(khatabookValidation)) {
            errors.add(khatabookValidation);
        }

        ProblemDetail customerValidation = doCustomerValidation(customerDTO.khatabookId(), customerDTO.msisdn());
        if (customerValidation != null) {
            errors.add(customerValidation);
        }

        return Collections.emptyList();
    }

    private ProblemDetail doCustomerValidation(final String khatabookId, final String msisdn) {
        final CustomerDTO customerDetails = myCustomerService.getCustomerByMsisdn(khatabookId, msisdn);
        if (Objects.isNull(customerDetails)) {
            return new NotFoundException(AppEntity.MSISDN, msisdn).get();
        }
        return null;
    }

    private ProblemDetail doKhatabookValidation(final String khatabookId) {
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return new NotFoundException(AppEntity.KHATABOOK, khatabookId).get();
        }
        return null;
    }


}
