package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.rating.exceptions.AppEntity;
import com.generic.khatabook.rating.exceptions.NotFoundException;
import com.generic.khatabook.rating.exchanger.CustomerClient;
import com.generic.khatabook.rating.model.CustomerView;
import com.generic.khatabook.rating.services.CustomerProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.LinkedHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerProxyServiceImpl implements CustomerProxyService {

    @Autowired
    private CustomerClient customerClient;

    @Override
    public CustomerView getCustomerById(final String customerId) {
        try {
            final ResponseEntity<?> customerBody = customerClient.getCustomerById(customerId);
            final LinkedHashMap<String, Object> customer = (LinkedHashMap<String, Object>) customerBody.getBody();
            return CustomerView.of(customerId, String.format("%s %s", customer.get("firstName"), customer.get(
                    "lastName")));
        } catch (WebClientRequestException | WebClientResponseException.ServiceUnavailable e) {
            final ProblemDetail prodNotFound = new NotFoundException(AppEntity.CUSTOMER, customerId).get();
            log.error(prodNotFound.getDetail(), e);
            return  CustomerView.of(customerId);
        }
    }
}
