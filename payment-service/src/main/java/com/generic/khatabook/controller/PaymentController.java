package com.generic.khatabook.controller;

import com.generic.khatabook.exchanger.CustomerClient;
import com.generic.khatabook.exchanger.KhatabookClient;
import com.generic.khatabook.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    @Autowired
    private CustomerClient myCustomerClient;

    @Autowired
    private KhatabookClient myKhatabookClient;

    @Autowired
    private PaymentService myPaymentService;
/*
    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/pay")
    public ResponseEntity<?> gavenToCustomer(@PathVariable String khatabookId, @PathVariable String customerId,
                                             @RequestBody PaymentDTO payment) {

        val khatabook = myKhatabookClient.getKhatabookDetails(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getCustomerByCustomerId(khatabookId, customerId);

        if (Objects.isNull(customer)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }


        try {
            myPaymentService.savePayment(khatabook, customer.get(), payment, PaymentType.CREDIT);
        } catch (InvalidArgumentValueException e) {

            return ResponseEntity.of(e.get()).build();
        }

        return ResponseEntity.ok().build();
    }*/
/*

    @PostMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/pay")
    public ResponseEntity<?> gavenToCustomerByMsisdn(@PathVariable String khatabookId,
                                                     @PathVariable String msisdn,
                                                     @RequestBody PaymentDTO payment) {

        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByMsisdn(payment.to());
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
        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByCustomerId(payment.from());
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

        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByMsisdn(msisdn);

        myPaymentService.savePayment(khatabook, customer, payment, PaymentType.DEBIT);

        return ResponseEntity.ok().build();
    }
*/

}
