package com.generic.khatabook.controller;

import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
@RestController
@RequestMapping(path = "khatabook-service")
public class KhatabookController {

    private static final String NULL = null;
    @Autowired
    private KhatabookService myKhatabookService;
    @Autowired
    private CustomerService myCustomerService;
    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/khatabooks")
    public List<KhatabookDTO> khatabookList() {
        return myKhatabookService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity<KhatabookDTO> createKhatabook(@Valid @RequestBody KhatabookDTO khatabook) {

        val khatabookRequest = khatabook.copyOf(myIdGeneratorService.generateId());

        if (!myKhatabookService.isValid(khatabookRequest)) {
            myKhatabookService.create(khatabookRequest);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/khatabookId/{khatabookId}").buildAndExpand(khatabookRequest.khatabookId()).toUri()).body(khatabookRequest);
        } else {
            return ResponseEntity.badRequest().body(khatabook);
        }
    }

    @GetMapping("/msisdn/{msisdn}")
    public KhatabookDTO deleteByMsisdn(@PathVariable String msisdn) {
        return myKhatabookService.delete(NULL, msisdn);
    }

    @GetMapping("/khatabookId/{khatabookId}")
    public KhatabookDTO getById(@PathVariable String khatabookId) {
        return myKhatabookService.getKhatabookByKhatabookId(khatabookId);
    }

    @GetMapping("/khatabookId/{khatabookId}/exist")
    public boolean isExist(@PathVariable String khatabookId) {
        return myKhatabookService.isExist(khatabookId);
    }


    @DeleteMapping("/khatabookId/{khatabookId}")
    public KhatabookDTO deleteById(@PathVariable String khatabookId) {
        return myKhatabookService.delete(khatabookId, NULL);
    }

    @DeleteMapping("/")
    public KhatabookDTO deleteByLastIndex() {
        return myKhatabookService.delete(NULL, NULL);
    }


    @PutMapping("/")
    public KhatabookDTO updateKhatabook(@RequestBody KhatabookDTO khatabookDTO) {
        return myKhatabookService.update(khatabookDTO);
    }

}
