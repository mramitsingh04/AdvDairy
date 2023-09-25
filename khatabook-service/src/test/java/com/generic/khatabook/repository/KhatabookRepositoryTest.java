package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Khatabook;
import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.entity.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class KhatabookRepositoryTest {
    public static final String PERSONAL = "Personal";
    public static final String LOAN = "Loan";
    @Autowired
    KhatabookGroupRepository khatabookGroupRepository;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    KhatabookRepository khatabookRepository;


    void saveOperator() {

        var operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        operatorRepository.save(operator);
    }

    @Test
    void testSaveKhatabook() {


        saveKhathBookGroup();

        var personalGroup = khatabookGroupRepository.findByGroupName(PERSONAL).orElse(null);
        var loanGroup = khatabookGroupRepository.findByGroupName(LOAN).orElse(null);

        List<Khatabook> listOfKhatabook = new ArrayList<>();

        listOfKhatabook.add(Khatabook.builder().khatabookId("Amit-Khatabook").status("Active").msisdn("8920801125").khatabookGroup(personalGroup).createdOn(LocalDateTime.now()).build());
        listOfKhatabook.add(Khatabook.builder().khatabookId("Priya-Khatabook").status("Active").msisdn("8709413839").khatabookGroup(personalGroup).createdOn(LocalDateTime.now()).build());
        listOfKhatabook.add(Khatabook.builder().khatabookId("Arpit-Khatabook").status("Active").msisdn("8709413839").khatabookGroup(loanGroup).createdOn(LocalDateTime.now()).build());


        khatabookRepository.saveAll(listOfKhatabook);

        KhatabookGroup personalGroupSaved = khatabookGroupRepository.findByGroupName(PERSONAL).orElse(null);
        assertNotNull(personalGroupSaved);

        assertNull(personalGroupSaved.getKhatabooks());
        assertThat(khatabookRepository.findByKhatabookId("Amit-Khatabook").isPresent()).isTrue();
        assertThat(khatabookRepository.findByKhatabookId("Arpit-Khatabook").isPresent()).isTrue();
        assertThat(khatabookRepository.findByKhatabookId("Priya-Khatabook").isPresent()).isTrue();


    }


    private void saveKhathBookGroup() {

        saveOperator();
        var operator = operatorRepository.findById("OPT01").orElse(null);
        KhatabookGroup personalGroup = KhatabookGroup.builder().operator(operator).groupName(PERSONAL).build();
        KhatabookGroup loanGroup = KhatabookGroup.builder().operator(operator).groupName("Loan").build();
        khatabookGroupRepository.save(personalGroup);
        khatabookGroupRepository.save(loanGroup);
    }

}