package com.generic.khatabook.notebook.service.proxy;

import com.generic.khatabook.notebook.exceptions.AppEntity;
import com.generic.khatabook.notebook.exceptions.NotFoundException;
import com.generic.khatabook.notebook.exchanger.CustomerClient;
import com.generic.khatabook.notebook.model.KhatabookCustomerView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerProxyServiceImpl implements CustomerProxyService {


    @Autowired
    private CustomerClient myCustomerClient;

    @Override
    public KhatabookCustomerView getCustomerByCustomerId(String khatabookId, String customerId) {
        KhatabookCustomerView customerView = null;
        try {
            ResponseEntity<EntityModel<KhatabookCustomerView>> productDetails = myCustomerClient.getCustomerByCustomerId(khatabookId, customerId);
            if (nonNull(productDetails.getBody())) {
                customerView = productDetails.getBody().getContent();
            }
            return customerView;

        } catch (WebClientRequestException | WebClientResponseException.ServiceUnavailable e) {
            final ProblemDetail prodNotFound = new NotFoundException(AppEntity.CUSTOMER, customerId).get();
            log.error(prodNotFound.getDetail(), e);
        }

        return customerView;
    }

}
