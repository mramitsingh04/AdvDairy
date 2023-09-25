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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
@RestController
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

    @GetMapping("/khatabook/khatabooks")
    public List<KhatabookDTO> khatabookList() {
        return myKhatabookService.getAll();
    }

    @PostMapping("/khatabook/khatabook")
    public ResponseEntity<KhatabookDTO> createKhatabook(@Valid @RequestBody KhatabookDTO khatabook) {

        val khatabookRequest = khatabook.copyOf(myIdGeneratorService.generateId());

        if (!myKhatabookService.isValid(khatabookRequest)) {
            myKhatabookService.create(khatabookRequest);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/khatabookId/{khatabookId}").buildAndExpand(khatabookRequest.khatabookId()).toUri()).body(khatabookRequest);
        } else {
            return ResponseEntity.badRequest().body(khatabook);
        }
    }

    @GetMapping("/khatabook/khatabook/msisdn/{msisdn}")
    public KhatabookDTO deleteByMsisdn(@PathVariable String msisdn) {
        return myKhatabookService.delete(NULL, msisdn);
    }

    @GetMapping("/khatabook/khatabook/khatabookId/{khatabookId}")
    public KhatabookDTO getById(@PathVariable String khatabookId) {
        return myKhatabookService.getKhatabookByKhatabookId(khatabookId);
    }


    @DeleteMapping("/khatabook/khatabook/khatabookId/{khatabookId}")
    public KhatabookDTO deleteById(@PathVariable String khatabookId) {
        return myKhatabookService.delete(khatabookId, NULL);
    }

    @DeleteMapping("/khatabook/khatabook/")
    public KhatabookDTO deleteByLastIndex() {
        return myKhatabookService.delete(NULL, NULL);
    }


    @PutMapping("/khatabook/khatabook")
    public KhatabookDTO updateKhatabook(@RequestBody KhatabookDTO khatabookDTO) {
        return myKhatabookService.update(khatabookDTO);
    }

}
