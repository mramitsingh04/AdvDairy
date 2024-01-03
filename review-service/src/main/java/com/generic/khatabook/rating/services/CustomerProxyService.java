package com.generic.khatabook.rating.services;


import com.generic.khatabook.rating.model.CustomerView;

public interface CustomerProxyService {
CustomerView getCustomerById(String customerId);
}
