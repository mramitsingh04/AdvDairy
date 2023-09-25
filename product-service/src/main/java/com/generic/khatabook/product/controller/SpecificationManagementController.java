package com.generic.khatabook.product.controller;

import com.generic.khatabook.product.exceptions.AppEntity;
import com.generic.khatabook.product.exceptions.NotFoundException;
import com.generic.khatabook.product.services.SpecificationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.Objects.isNull;
import com.generic.khatabook.product.model.*;
@RestController("specification")
public class SpecificationManagementController {


    private SpecificationManagementService mySpecificationManagementService;

    @Autowired
    public SpecificationManagementController(final SpecificationManagementService specificationManagementService) {
        mySpecificationManagementService = specificationManagementService;
    }

    @PostMapping(path = "/specification")
    public ResponseEntity<?> create(@RequestBody SpecificationDTO specification) {

        if (mySpecificationManagementService.findById(specification.id())) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), specification.id() + " already exist.")).build();
        }
        final SpecificationDTO savedSpecification = mySpecificationManagementService.addSpecification(specification);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{specificationId}").buildAndExpand(savedSpecification.id()).toUri()).body(savedSpecification);
    }

    @GetMapping(path = "/specifications")
    public ResponseEntity<?> getAllSpecification() {
        return ResponseEntity.ok(mySpecificationManagementService.findAll());
    }

    @GetMapping("specification/{specificationId}")
    public ResponseEntity<SpecificationDTO> getSpecificationId(@PathVariable String specificationId) {

        final SpecificationDTO specification = mySpecificationManagementService.find(specificationId);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, specificationId).get()).build();
        }
        return ResponseEntity.ok(specification);
    }

    @DeleteMapping("specification/{specificationId}")
    public ResponseEntity<SpecificationDTO> deleteSpecificationId(@PathVariable String specificationId) {
        final SpecificationDTO specification = mySpecificationManagementService.find(specificationId);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, specificationId).get()).build();
        }
        mySpecificationManagementService.deleteSpecifications(specification);
        return ResponseEntity.ok(specification);
    }

    @PutMapping("specification/{specificationId}")
    public ResponseEntity<?> updateSpecificationId(@PathVariable String specificationId, @RequestBody SpecificationDTO specificationDTO) {
        final SpecificationDTO specification = mySpecificationManagementService.find(specificationId);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, specificationId).get()).build();
        }
        return ResponseEntity.ok(mySpecificationManagementService.updateSpecification(specificationDTO));
    }
}

