package com.generic.khatabook.controller;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.DuplicateFoundException;
import com.generic.khatabook.exceptions.InvalidArgumentException;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exceptions.ResourceFoundException;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.Containers;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.model.CustomerProductSpecificationUpdatable;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.CustomerSpecificationService;
import com.generic.khatabook.service.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("customer-service")
@RequiredArgsConstructor
public class CustomerSpecificationController {

    private final CustomerSpecificationService myCustomerSpecificationService;
    private final IdGeneratorService myIdGeneratorService;
    private final CustomerService myCustomerService;



    @GetMapping(path = "/{khatabookId}/customer/{customerId}/specifications")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> create(@PathVariable String khatabookId,
                                    @PathVariable String customerId,
                                    @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {


        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        if (!Objects.equals(khatabookId, customerDetails.khatabookId())) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        if (nonNull(customerDetails.specification())) {
            return ResponseEntity.of(new ResourceFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                    customerDetails.specification().id()).get()).build();
        }


        customerSpecificationDTO = customerSpecificationDTO.copyOf(myIdGeneratorService.generateId());

        ResponseEntity<?> checkForDuplicateRequestCreation = checkForDuplicateRequestCreation(customerSpecificationDTO);
        if (checkForDuplicateRequestCreation.getStatusCode()!=HttpStatus.OK)
            return checkForDuplicateRequestCreation;


        myCustomerSpecificationService.mergeSpecification(customerDetails, customerSpecificationDTO);


        final CustomerSpecificationDTO saved = myCustomerSpecificationService.save(customerSpecificationDTO);


        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{khatabookId" + "}/customer" + "/{customerId" + "}/specification" + "/{specificationId}").buildAndExpand(
                khatabookId,
                customerId,
                saved.id()).toUri()).body(saved);
    }

    private ResponseEntity<Object> checkForDuplicateRequestCreation(final CustomerSpecificationDTO customerSpecificationDTO) {

        if (isNull(customerSpecificationDTO.id())) {
            return ResponseEntity.ok().build();
        }
        if (myCustomerSpecificationService.get(customerSpecificationDTO.id()).isPresent()) {
            return ResponseEntity.of(new DuplicateFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                    customerSpecificationDTO.id()).get()).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<CustomerSpecificationDTO> getById(@PathVariable String khatabookId,
                                                            @PathVariable String customerId,
                                                            @PathVariable String specificationId) {

        Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> specificationDTOS = myCustomerSpecificationService.getCustomerSpecification(
                specificationId);
        return ResponseEntity.ok(specificationDTOS.get());
    }

    @DeleteMapping("/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> deleteById(@PathVariable String khatabookId, @PathVariable String customerId) {
        Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> specificationDTOS = myCustomerSpecificationService.getCustomerSpecification(
                khatabookId,
                customerId);

        specificationDTOS.forEach(x -> myCustomerSpecificationService.delete(x.get()));
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String khatabookId,
                                            @PathVariable String customerId,
                                            @PathVariable String specificationId,
                                            @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {

        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<?> patchUpdate1(@PathVariable String khatabookId,
                                          @PathVariable String customerId,
                                          @PathVariable String specificationId,
                                          @RequestBody CustomerSpecificationDTO dto) {


        final CustomerSpecificationUpdatable entityModel = myCustomerSpecificationService.getCustomerSpecification(
                specificationId).updatable();
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                    specificationId).get()).build();
        }

        if (nonNull(dto.description())) {
            entityModel.setCustomerId(dto.description());
        }
        if (nonNull(dto.name())) {
            entityModel.setName(dto.name());
        }
        if (nonNull(dto.products())) {
            List<CustomerProductSpecificationDTO> newProduct = new ArrayList<>();
            for (CustomerProductSpecificationUpdatable entityModelProduct : entityModel.getProducts()) {
                for (CustomerProductSpecificationDTO product : dto.products()) {
                    if (entityModelProduct.getProductId().equals(product.productId())) {
                        newProduct.add(product);

                    } else {

                    }
                }
            }
        }

        entityModel.setVersion(entityModel.getVersion() + 1);
        entityModel.setUpdatedOn(LocalDateTime.now(Clock.systemDefaultZone()));
        final CustomerSpecificationDTO updateProduct = myCustomerSpecificationService.update(entityModel.build());
        return ResponseEntity.ok(updateProduct);
    }


    public ResponseEntity<?> patchUpdate(@PathVariable String khatabookId,
                                         @PathVariable String customerId,
                                         @PathVariable String specificationId,
                                         @RequestBody Map<String, Object> requestMap) {


        final CustomerSpecificationUpdatable entityModel = myCustomerSpecificationService.getCustomerSpecification(
                specificationId).updatable();
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                    specificationId).get()).build();
        }
        for (final Map.Entry<String, Object> member : requestMap.entrySet()) {
            final Field field = ReflectionUtils.findField(CustomerSpecificationUpdatable.class, member.getKey());
            if (nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                final Object valueToSet = member.getValue();

                if (List.class.getName().equals(field.getType().getName())) {
                    updateSubEntityOfCustomerProductSpecification(entityModel, field, valueToSet);
                } else if (BigDecimal.class.getName().equals(field.getType().getName())) {
                    ReflectionUtils.setField(field, entityModel, BigDecimal.valueOf((Double) valueToSet));
                } else {
                    ReflectionUtils.setField(field, entityModel, valueToSet);
                }
            } else {
                throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, member.getKey());
            }
        }
        entityModel.setVersion(entityModel.getVersion() + 1);
        entityModel.setUpdatedOn(LocalDateTime.now(Clock.systemDefaultZone()));
        final CustomerSpecificationDTO updateProduct = myCustomerSpecificationService.update(entityModel.build());
        return ResponseEntity.ok(updateProduct);
    }

    private void updateSubEntityOfCustomerProductSpecification(final CustomerSpecificationUpdatable entityModel,
                                                               final Field field,
                                                               final Object valueToSet) {

        switch (field.getGenericType().getTypeName()) {
            case "java.util.List<com.generic.khatabook.specification.model.CustomerProductSpecificationUpdatable>" ->
                    updateCustomerProductSpecificationUpdatable(
                            entityModel,
                            (List<LinkedHashMap<String, Object>>) valueToSet);
            default ->
                    throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, field.getName());
        }
    }

    private CustomerSpecificationUpdatable updateCustomerProductSpecificationUpdatable(final CustomerSpecificationUpdatable entityModel,
                                                                                       final List<LinkedHashMap<String, Object>> valueToSet) {
        List<LinkedHashMap<String, Object>> mapValue = valueToSet;
        for (final LinkedHashMap<String, Object> eachProduct : mapValue) {

            final String productId = (String) eachProduct.get("productId");

            final CustomerProductSpecificationUpdatable oldProductSpecification = entityModel.getProducts(productId);
            for (final Map.Entry<String, Object> customerProductSpecification : eachProduct.entrySet()) {
                final Field eachField = ReflectionUtils.findField(CustomerProductSpecificationUpdatable.class,
                        customerProductSpecification.getKey());
                if (isNull(eachField)) {
                    throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, eachField.getName());
                }
                ReflectionUtils.makeAccessible(eachField);
                setValueInField(oldProductSpecification, customerProductSpecification.getValue(), eachField);
                if (isNull(oldProductSpecification.getId())) {
                    entityModel.addProduct(oldProductSpecification);
                }
            }
            return entityModel;

        }
        return null;
    }

    private void setValueInField(final CustomerProductSpecificationUpdatable oldProductSpecification,
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
