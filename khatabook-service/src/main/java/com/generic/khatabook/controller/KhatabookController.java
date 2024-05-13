package com.generic.khatabook.controller;

import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookGroupDTO;
import com.generic.khatabook.model.KhatabookView;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhataBookGroupService;
import com.generic.khatabook.service.KhatabookRestService;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "khatabook-service")
public class KhatabookController {

    private static final String NULL = null;
    @Autowired
    private KhatabookRestService khatabookRestService;
    @Autowired
    private KhataBookGroupService khataBookGroupService;
    @Autowired
    private CustomerService myCustomerService;
    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/khatabooks")
    public List<KhatabookView> khatabookList(@RequestParam(required = false) String[] groups) {
        if (Objects.nonNull(groups)) {
            List<KhatabookGroupDTO> allFoundGroups = khataBookGroupService.getAllGroups(groups);
            return khatabookRestService.getAll(allFoundGroups.stream().map(KhatabookGroupDTO::groupId).toList());
        }
        return khatabookRestService.getAll();
    }

    @GetMapping("/V1/khatabooks")
    public List<KhatabookView> getAllKhatabook(@RequestParam(required = false) String[] groups) {
        if (Objects.nonNull(groups)) {
            List<KhatabookGroupDTO> allFoundGroups = khataBookGroupService.getAllGroups(groups);
            return khatabookRestService.getAll(allFoundGroups.stream().map(KhatabookGroupDTO::groupId).toList());
        }
        return khatabookRestService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity<KhatabookDTO> createKhatabook(@Valid @RequestBody KhatabookDTO khatabook) {

        val khatabookRequest = khatabook.copyOf(myIdGeneratorService.generateId());

        if (!khatabookRestService.isValid(khatabookRequest)) {
            khatabookRestService.create(khatabookRequest);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{khatabookId}").buildAndExpand(khatabookRequest.khatabookId()).toUri()).body(khatabookRequest);
        } else {
            return ResponseEntity.badRequest().body(khatabook);
        }
    }

    @GetMapping("/msisdn/{msisdn}")
    public KhatabookView deleteByMsisdn(@PathVariable String msisdn) {
        return khatabookRestService.delete(NULL, msisdn);
    }

    @GetMapping("/{khatabookId}")
    public KhatabookView getById(@PathVariable String khatabookId) {
        return khatabookRestService.getKhatabookByKhatabookId(khatabookId);
    }

    @GetMapping("/{khatabookId}/exist")
    public boolean isExist(@PathVariable String khatabookId) {
        return khatabookRestService.isExist(khatabookId);
    }


    @DeleteMapping("/{khatabookId}")
    public KhatabookView deleteById(@PathVariable String khatabookId) {
        return khatabookRestService.delete(khatabookId, NULL);
    }

    @DeleteMapping("/")
    public KhatabookView deleteByLastIndex() {
        return khatabookRestService.delete(NULL, NULL);
    }


    @PutMapping("/")
    public KhatabookView updateKhatabook(@RequestBody KhatabookDTO khatabookDTO) {
        return khatabookRestService.update(khatabookDTO);
    }

}
