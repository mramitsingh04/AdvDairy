package com.generic.khatabook.controller;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.KhatabookDetails;
import com.generic.khatabook.model.KhatabookDetailsView;
import com.generic.khatabook.model.KhatabookPaymentSummaryView;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.Product;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentException;
import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.CustomerSpecificationService;
import com.generic.khatabook.service.KhatabookService;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.validator.CustomerValidation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {
    public static final String ASC_DESC = "asc,desc";
    public static final String DATE_CUSTOMER_PRODUCT = "date,customer,product";
    public static final String SORTING_MSG = "%s is invalid value for sorting, possible value will be (%s).";
    @Autowired
    private CustomerService myCustomerService;

    @Autowired
    private KhatabookService myKhatabookService;

    @Autowired
    private PaymentService myPaymentService;

    @Autowired
    private CustomerValidation myCustomerValidation;

    @Autowired
    private CustomerSpecificationService customerSpecificationService;

    @GetMapping(path = "/khatabook/{khatabookId}/customers")
    @PostMapping
    public ResponseEntity<KhatabookDetails> getKhatabookDetails(@PathVariable String khatabookId) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        KhatabookDetails khatabookDetails = new KhatabookDetails(khatabook,
                myCustomerService.getAll(khatabookId),
                myPaymentService.getPaymentDetailByKhatabookId(
                        khatabookId));
        return ResponseEntity.ok(khatabookDetails);
    }

    @PostMapping(path = "/khatabook/{khatabookId}/customer")
    public ResponseEntity<?> createCustomer(@PathVariable String khatabookId, @RequestBody CustomerDTO customerDTO) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        CustomerDTO savedCustomer;
        try {
            savedCustomer = myCustomerService.save(customerDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.of(e.get()).build();
        }
        EntityModel<CustomerDTO> entityModel = EntityModel.of(savedCustomer);
        entityModel.add(linkTo(methodOn(CustomerController.class).getCustomerByCustomerId(null, null, khatabookId,
                savedCustomer.customerId())).withSelfRel());
        entityModel.add(linkTo(methodOn(CustomerController.class).getCustomerByMsisdn(khatabookId,
                savedCustomer.msisdn())).withSelfRel());

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{specificationId}").buildAndExpand(
                customerDTO.customerId()).toUri()).body(entityModel);
    }

    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}", produces = {"application/json"})
    public ResponseEntity<?> getCustomerByCustomerId(
            @RequestParam(required = false, defaultValue = "desc") String sorting,
            @RequestParam(required = false, defaultValue = "date") String sortingBy,
            @PathVariable String khatabookId,
            @PathVariable String customerId

    ) {
//amit testing not done

        if (nonNull(sorting) && !isSortingPossibleValueValid(sorting)) {
            return ResponseEntity.of(new InvalidArgumentValueException(SORTING_MSG.formatted(sorting, ASC_DESC)).get()).build();
        }

        if (nonNull(sortingBy) && !isSortingByPossibleValueValid(sortingBy)) {
            return ResponseEntity.of(new InvalidArgumentValueException("%s is invalid value for sortingBy, " +
                    "possible value will be (%s).".formatted(sorting, DATE_CUSTOMER_PRODUCT)).get()).build();
        }


        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }
        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        CustomerSpecificationDTO customerSpecification = null;
        if (nonNull(customerDetails.specification())) {

            customerSpecification = customerSpecificationService.getCustomerSpecification(customerDetails.specification().id()).get();
        }

        final KhatabookPaymentSummaryView customerDairy = myPaymentService.getCustomerPaymentDetailView(customerDetails, customerSpecification);


        KhatabookDetailsView khatabookDetails = new KhatabookDetailsView(khatabook, customerDetails, customerDairy, customerSpecification);
        final String customerLink = khatabookDetails.getCustomers().stream().findFirst().map(CustomerDTO::customerId).orElse(
                null);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomer(khatabookId,
                customerLink,
                PaymentDTO.nullOf())).withRel(
                "PayTo");

        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomer(khatabookId,
                customerLink,
                PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPayment(khatabookId,
                customerLink,
                null)).withRel(
                "Aggregate");

        EntityModel<KhatabookDetailsView> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    private boolean isSortingByPossibleValueValid(final String sortingBy) {
        return DATE_CUSTOMER_PRODUCT.contains(sortingBy);
    }

    private boolean isSortingPossibleValueValid(final String sorting) {
        return ASC_DESC.contains(sorting);
    }

    @GetMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}", produces = {"application/hal+json"})
    public ResponseEntity<?> getCustomerByMsisdn(@PathVariable String khatabookId, @PathVariable String msisdn) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final CustomerDTO customerDetails = myCustomerService.getCustomerByMsisdn(khatabookId, msisdn);
        if (isNull(customerDetails)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        final KhatabookPaymentSummaryView customerDairy = myPaymentService.getCustomerPaymentDetailView(customerDetails, null);
        CustomerSpecificationDTO customerSpecification = null;
        if (nonNull(customerDetails.specification())) {

            customerSpecification = customerSpecificationService.getCustomerSpecification(customerDetails.specification().id()).get();
        }

        KhatabookDetailsView khatabookDetails = new KhatabookDetailsView(khatabook, customerDetails, customerDairy, customerSpecification);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomerByMsisdn(khatabookId,
                msisdn,
                PaymentDTO.nullOf())).withRel(
                "PayTo");
        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomerByMsisdn(khatabookId,
                msisdn,
                PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPaymentByMsisdn(
                khatabookId,
                msisdn,
                null)).withRel("Aggregate");

        EntityModel<KhatabookDetailsView> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}")
    public ResponseEntity<?> deleteByMsisdn(@PathVariable String msisdn) {

        final CustomerDTO customerDetails = myCustomerService.getByMsisdn(msisdn);
        if (isNull(customerDetails)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }


        myCustomerService.delete(null, customerDetails.msisdn());

        return ResponseEntity.ok(customerDetails);
    }

    @DeleteMapping(path = "/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<CustomerDTO> deleteByCustomerId(@PathVariable String customerId) {
        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }


        myCustomerService.delete(customerId, customerDetails.msisdn());

        return ResponseEntity.ok(customerDetails);
    }

    @PutMapping(path = "/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<EntityModel<KhatabookDetailsView>> updateCustomer(@PathVariable String khatabookId,
                                                                            @PathVariable String customerId,
                                                                            @RequestBody CustomerDTO customerDTO) {

        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(customerDetails.khatabookId());
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final KhatabookPaymentSummaryView customerDairy = myPaymentService.getCustomerPaymentDetailView(customerDetails, null);


        CustomerSpecificationDTO customerSpecification = null;
        if (nonNull(customerDetails.specification())) {

            customerSpecification = customerSpecificationService.getCustomerSpecification(customerDetails.specification().id()).get();
        }

        KhatabookDetailsView khatabookDetails = new KhatabookDetailsView(khatabook, customerDetails, customerDairy, customerSpecification);
        final String customerLink = khatabookDetails.getCustomers().stream().findFirst().map(CustomerDTO::customerId).orElse(
                null);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomer(khatabookId,
                customerLink,
                PaymentDTO.nullOf())).withRel(
                "PayTo");

        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomer(khatabookId,
                customerLink,
                PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPayment(khatabookId,
                customerLink,
                null)).withRel(
                "Aggregate");

        EntityModel<KhatabookDetailsView> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    @PatchMapping(path = "/khatabook/{khatabookId}/v1/customer/{customerId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialCustomer(
            @RequestParam String version,
            @PathVariable String khatabookId,
            @PathVariable String customerId,
            @RequestBody Map<String, Object> customerEntities) {
        final CustomerUpdatable customerDetails = myCustomerService.getByCustomerId(customerId).updatable();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        for (final Map.Entry<String, Object> member : customerEntities.entrySet()) {
            final Field field = ReflectionUtils.findField(CustomerUpdatable.class, member.getKey());
            if (nonNull(field)) {
                field.setAccessible(true);
                final Object valueToSet = member.getValue();
                if (List.class.getName().equals(field.getType().getName())) {
                    updateSubEntityOfCustomerProduct(customerDetails, field, valueToSet);
                } else if (BigDecimal.class.getName().equals(field.getType().getName())) {
                    ReflectionUtils.setField(field, customerDetails, BigDecimal.valueOf((Double) valueToSet));
                } else {
                    ReflectionUtils.setField(field, customerDetails, valueToSet);
                }
            } else {
                throw new InvalidArgumentException(AppEntity.CUSTOMER, member.getKey());
            }
        }

        if (nonNull(customerDetails.getProducts())) {
            final ProblemDetail customerProductValidation = myCustomerValidation.doCustomerProductValidation(
                    customerDetails.getProducts());
            if (nonNull(customerProductValidation)) {
                return ResponseEntity.of(customerProductValidation).build();
            }
        }

        final CustomerDTO updateCustomer = myCustomerService.update(customerDetails.build());

        return ResponseEntity.ok(updateCustomer);
    }

    private void updateSubEntityOfCustomerProduct(final CustomerUpdatable customerDetails,
                                                  final Field field,
                                                  final Object valueToSet) {

        switch (field.getGenericType().getTypeName()) {
            case "java.util.List<com.generic.khatabook.model.Product>" ->
                    updateCustomerProductUpdatable(
                            customerDetails,
                            (List<LinkedHashMap<String, Object>>) valueToSet);
            default ->
                    throw new InvalidArgumentException(com.generic.khatabook.exceptions.AppEntity.CUSTOMER, field.getName());
        }
    }

    private CustomerUpdatable updateCustomerProductUpdatable(final CustomerUpdatable customerDetails,
                                                             final List<LinkedHashMap<String, Object>> valueToSet) {
        List<LinkedHashMap<String, Object>> mapValue = valueToSet;
        for (final LinkedHashMap<String, Object> eachProduct : mapValue) {

            final String productId = (String) eachProduct.get("productId");
            final String productName = (String) eachProduct.get("name");

            final CustomerUpdatable oldProductSpecification = customerDetails.addProduct(productId, productName);
            for (final Map.Entry<String, Object> customerProductSpecification : eachProduct.entrySet()) {
                final Field eachField = ReflectionUtils.findField(Product.class,
                        customerProductSpecification.getKey());
                if (isNull(eachField)) {
                    throw new InvalidArgumentException(com.generic.khatabook.exceptions.AppEntity.PRODUCT, eachField.getName());
                }
                ReflectionUtils.makeAccessible(eachField);
                setValueInField(oldProductSpecification, customerProductSpecification.getValue(), eachField);
                if (isNull(oldProductSpecification.getCustomerId())) {
                    //  customerDetails.addProduct(oldProductSpecification);
                }
            }
        }
        return customerDetails;
    }

    @PatchMapping(path = "/khatabook/{khatabookId}/customer/{customerId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialCustomerV2(
            @RequestParam String version,
            @PathVariable String khatabookId,
            @PathVariable String customerId,
            @RequestBody CustomerDTO customerEntities) {
        final CustomerUpdatable customerDetails = myCustomerService.getByCustomerId(customerId).updatable();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        if (nonNull(customerDetails.getProducts())) {
            final ProblemDetail customerProductValidation = myCustomerValidation.doCustomerProductValidation(
                    customerDetails.getProducts());
            if (nonNull(customerProductValidation)) {
                return ResponseEntity.of(customerProductValidation).build();
            }
        }

        final CustomerDTO updateCustomer = myCustomerService.update(customerEntities);

        return ResponseEntity.ok(updateCustomer);
    }

    private void setValueInField(final CustomerUpdatable oldProductSpecification,
                                 final Object valueToSet,
                                 final Field eachField) {
        if (eachField.getGenericType().getTypeName().equals("float")) {
            ReflectionUtils.setField(eachField,
                    oldProductSpecification,
                    BigDecimal.valueOf((Double) valueToSet).floatValue());
        }
        if (BigDecimal.class.getName().equals(eachField.getType().getName())) {
            ReflectionUtils.setField(eachField, oldProductSpecification, BigDecimal.valueOf((Double) valueToSet));
        }
    }
}
