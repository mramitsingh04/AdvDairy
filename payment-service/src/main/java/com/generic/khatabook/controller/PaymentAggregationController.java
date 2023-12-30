package com.generic.khatabook.controller;

import org.springframework.web.bind.annotation.RestController;
@RestController
public class PaymentAggregationController {

/*

    @Autowired
    private CustomerClient myCustomerClient;

    @Autowired
    private KhatabookClient myKhatabookClient;

    @Autowired
    private AggregatePaymentService myAggregatePaymentService;


    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/aggregate/all")
    public ResponseEntity<?> aggregatedAllPayment(@PathVariable String khatabookId, @PathVariable String customerId, @RequestBody AggregatePaymentDTO payment) {

        val khatabook = myKhatabookClient.getKhatabookDetails(khatabookId);
        final KhatabookDetails khatabookDetails = khatabook.getBody();
        if (Objects.isNull(khatabookDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getCustomerByCustomerId(khatabookId, customerId);

        if (Objects.isNull(customer) || !customer.hasBody()) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }

        try {
            myAggregatePaymentService.allPaymentAggregate(khatabook.getBody(), customer.getBody(), payment);
        } catch (NoPaymentForAggregateException e) {
            return ResponseEntity.of(e.get()).build();
        }

        return ResponseEntity.ok().build();
    }
    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/aggregate")
    public ResponseEntity<?> aggregatedPayment(@PathVariable String khatabookId, @PathVariable String customerId, @RequestBody AggregatePaymentDTO payment) {

        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByCustomerId(customerId);

        if (Objects.isNull(customer)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }

        try {
            myAggregatePaymentService.paymentAggregate(khatabook, customer.get(), payment);
        } catch (NoPaymentForAggregateException e) {
            return ResponseEntity.of(e.get()).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/aggregate")
    public ResponseEntity<?> aggregatedPaymentByMsisdn(@Validated @PathVariable String khatabookId, @Validated @PathVariable String msisdn, @RequestBody AggregatePaymentDTO payment) {

        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByMsisdn(msisdn);

        if (Objects.isNull(customer)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        myAggregatePaymentService.paymentAggregate(khatabook, customer, payment);

        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/aggregate")
    public ResponseEntity<?> getLastAggregatedPayment(@PathVariable String khatabookId, @PathVariable String customerId) {

        val khatabook = myKhatabookClient.getKhatabookDetails(khatabookId);
        if (Objects.isNull(khatabook) || !khatabook.hasBody()) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByCustomerId(customerId);
        if (Objects.isNull(customer)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        return ResponseEntity.ok(myAggregatePaymentService.getLastAggregation(khatabook, customer.get()));
    }

    @GetMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/aggregate")
    public ResponseEntity<?> getLastAggregatedPaymentByMsisdn(@PathVariable String khatabookId, @PathVariable String msisdn) {

        val khatabook = myKhatabookClient.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final var customer = myCustomerClient.getByMsisdn(msisdn);
        if (Objects.isNull(customer)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        myAggregatePaymentService.getLastAggregation(khatabook, customer);

        return ResponseEntity.ok().build();
    }
*/

}
