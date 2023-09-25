package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Khatabook;
import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.entity.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class KhatabookGroupRepositoryTest {

    @Autowired
    KhatabookGroupRepository khatabookGroupRepository;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    KhatabookRepository khatabookRepository;

    Operator operator;

    @BeforeEach
    void setUp() {

        operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        operatorRepository.save(operator);
    }

    @Test
    public void testSaveKhatabookGroup() {

        Optional<Operator> optOpl01 = operatorRepository.findById("OPT01");

        List<Khatabook> listOfKhatabook = new ArrayList<>();

        listOfKhatabook.add(Khatabook.builder()
                .khatabookId("Amit-Khatabook")
                .status("Active")
                .msisdn("8920801125")
                .createdOn(LocalDateTime.now())
                .build());

        KhatabookGroup khatabookGroup = KhatabookGroup.builder()
                .operator(optOpl01.get())
                .groupName("Personal")
                .khatabooks(listOfKhatabook)
                .build();
        khatabookGroupRepository.save(khatabookGroup);

        Optional<KhatabookGroup> personalGroupId = khatabookGroupRepository.findByGroupName("Personal");
        assertNotNull(personalGroupId);
        assertNotNull(personalGroupId.get());
        assertNotNull(personalGroupId.get().getGroupId());
        assertNotNull(personalGroupId.get().getKhatabooks());


    }

    @Test
    public void testUpdateKhatabookGroupAddNewKhatabook() {

        testSaveKhatabookGroup();
        Optional<KhatabookGroup> personalGroupId = khatabookGroupRepository.findByGroupName("Personal");
        KhatabookGroup khatabookGroup = personalGroupId.get();
        khatabookGroup.getKhatabooks().add(Khatabook.builder()
                .khatabookId("Priya-Khatabook")
                .status("Active")
                .msisdn("8709413839")
                .createdOn(LocalDateTime.now())
                .build());
        khatabookGroup.getKhatabooks().add(Khatabook.builder()
                .khatabookId("Arpit-Khatabook")
                .status("Active")
                .msisdn("8709413839")
                .createdOn(LocalDateTime.now())
                .build());

        khatabookGroupRepository.save(khatabookGroup);

        personalGroupId = khatabookGroupRepository.findByGroupName("Personal");
        assertNotNull(personalGroupId);
        assertNotNull(khatabookGroup);
        assertNotNull(khatabookGroup.getGroupId());
        assertNotNull(khatabookGroup.getKhatabooks());

        assertThat(khatabookGroup.getKhatabooks()).hasSize(3);


    }


    @Test
    void testDeleteKhataGroup() {
        Optional<Operator> optOpl01 = operatorRepository.findById("OPT01");

        List<Khatabook> listOfKhatabook = new ArrayList<>();

        listOfKhatabook.add(Khatabook.builder()
                .khatabookId("Amit-Khatabook")
                .status("Active")
                .msisdn("8920801125")
                .createdOn(LocalDateTime.now())
                .build());
        listOfKhatabook.add(Khatabook.builder()
                .khatabookId("Priya-Khatabook")
                .status("Active")
                .msisdn("8709413839")
                .createdOn(LocalDateTime.now())
                .build());
        listOfKhatabook.add(Khatabook.builder()
                .khatabookId("Arpit-Khatabook")
                .status("Active")
                .msisdn("8709413839")
                .createdOn(LocalDateTime.now())
                .build());
        KhatabookGroup khatabookGroup = KhatabookGroup.builder()
                .operator(optOpl01.get())
                .groupName("Personal")
                .khatabooks(listOfKhatabook)
                .build();
        khatabookGroupRepository.save(khatabookGroup);

        Optional<KhatabookGroup> personalGroupId = khatabookGroupRepository.findByGroupName("Personal");
        assertNotNull(personalGroupId);
        assertThat(personalGroupId.isPresent()).isTrue();
        assertNotNull(personalGroupId.get().getGroupId());
        assertNotNull(personalGroupId.get().getKhatabooks());

        khatabookGroupRepository.delete(personalGroupId.get());
        personalGroupId = khatabookGroupRepository.findByGroupName("Personal");

        assertThat(personalGroupId.isPresent()).isFalse();

        assertThat(khatabookRepository.findByKhatabookId("Amit-Khatabook").isPresent()).isFalse();
        assertThat(khatabookRepository.findByKhatabookId("Arpit-Khatabook").isPresent()).isFalse();
        assertThat(khatabookRepository.findByKhatabookId("Priya-Khatabook").isPresent()).isFalse();


    }
}