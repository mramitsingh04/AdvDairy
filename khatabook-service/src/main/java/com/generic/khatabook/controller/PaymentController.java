package com.generic.khatabook.controller;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.PaymentType;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import com.generic.khatabook.service.PaymentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PaymentController {


    @Autowired
    private CustomerService myCustomerService;

    @Autowired
    private KhatabookService myKhatabookService;

    @Autowired
    private PaymentService myPaymentService;

    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/pay")
    public ResponseEntity<?> gavenToCustomer(@PathVariable String khatabookId, @PathVariable String customerId,
                                             @RequestBody PaymentDTO payment) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerService.getByCustomerId(customerId);

        if (Objects.isNull(customer)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }


        try {
            myPaymentService.savePayment(khatabook, customer.get(), payment, PaymentType.CREDIT);
        } catch (InvalidArgumentValueException e) {

            return ResponseEntity.of(e.get()).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/pay")
    public ResponseEntity<?> gavenToCustomerByMsisdn(@PathVariable String khatabookId,
                                                     @PathVariable String msisdn,
                                                     @RequestBody PaymentDTO payment) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerService.getByMsisdn(payment.to());
        if (Objects.isNull(customer)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.CUSTOMER, msisdn));
        }

        myPaymentService.savePayment(khatabook, customer, payment, PaymentType.CREDIT);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/receive")
    public ResponseEntity<?> receiveFromCustomer(@PathVariable String khatabookId,
                                                 @PathVariable String customerId,
                                                 @RequestBody PaymentDTO payment) {
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerService.getByCustomerId(payment.from());
        if (Objects.isNull(customer)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        myPaymentService.savePayment(khatabook, customer.get(), payment, PaymentType.DEBIT);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/receive")
    public ResponseEntity<?> receiveFromCustomerByMsisdn(@PathVariable String khatabookId,
                                                         @PathVariable String msisdn,
                                                         @RequestBody PaymentDTO payment) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerService.getByMsisdn(msisdn);

        myPaymentService.savePayment(khatabook, customer, payment, PaymentType.DEBIT);

        return ResponseEntity.ok().build();
    }

}
