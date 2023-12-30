package com.generic.khatabook.controller;

import com.generic.khatabook.model.KhatabookGroupDTO;
import com.generic.khatabook.service.KhataBookGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
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

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "khatabook-service")
public class KhatabookGroupController {

    private static final String NULL = null;
    @Autowired
    private KhataBookGroupService khataBookGroupService;


    @Autowired
    private MessageSource messageSource;

    @GetMapping("/groups")
    public ResponseEntity<List<KhatabookGroupDTO>> groups() {
        return ok(khataBookGroupService.getAll());
    }

    @PostMapping("/group")
    public ResponseEntity<KhatabookGroupDTO> create(@Valid @RequestBody KhatabookGroupDTO groupDTO) {

//        val operatorRequest = groupDTO.copyOf(myIdGeneratorService.generateId());

        if (!khataBookGroupService.isValid(groupDTO)) {
            khataBookGroupService.create(groupDTO);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/groupId/{groupId}").buildAndExpand(groupDTO.operatorId()).toUri()).body(groupDTO);
        } else {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), groupDTO.groupId() + " already exist.")).build();
        }
    }


    @GetMapping("/group/groupId/{groupId}")
    public ResponseEntity<KhatabookGroupDTO> getById(@PathVariable String groupId) {
        return ok(khataBookGroupService.get(groupId));
    }


    @DeleteMapping("/group/groupId/{groupId}")
    public ResponseEntity deleteById(@PathVariable String groupId) {
        khataBookGroupService.delete(groupId);
        return ok().build();
    }


    @PutMapping("/group")
    public ResponseEntity<KhatabookGroupDTO> update(@RequestBody KhatabookGroupDTO KhatabookGroupDTO) {
        return ok(khataBookGroupService.update(KhatabookGroupDTO));
    }

}
