package com.generic.khatabook.notebook.service.proxy;

import com.generic.khatabook.notebook.model.KhatabookCustomerView;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerProxyService {
    KhatabookCustomerView getCustomerByCustomerId(String khatabookId,String customerId);
}
