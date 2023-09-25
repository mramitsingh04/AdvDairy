package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.SpecificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
@HttpExchange
public interface SpecificationClient {

    @GetExchange("/specifications")
    public ResponseEntity<List<SpecificationDTO>> getAllSpecification();

    @GetExchange("/specification/{specificationId}")
    public ResponseEntity<SpecificationDTO> getSpecificationId(@PathVariable String id);

}
