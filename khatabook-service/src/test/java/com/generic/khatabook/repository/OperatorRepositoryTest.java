package com.generic.khatabook.repository;

import com.generic.khatabook.entity.KhatabookGroup;
import com.generic.khatabook.entity.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OperatorRepositoryTest {

    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveTheOperator() {
        Operator operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        operatorRepository.save(operator);

        Optional<Operator> opt01 = operatorRepository.findById("OPT01");
        assertTrue(opt01.isPresent());
        assertEquals(opt01.get().getOperatorId(), "OPT01");

    }

    @Test
    void shouldSaveOperatorAndFindAll() {
        Operator operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        Operator operator01 = Operator.builder()
                .operatorId("OPT02")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit01@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        operatorRepository.save(operator);
        operatorRepository.save(operator01);

        List<Operator> opt01 = operatorRepository.findAll();
        assertEquals(2, opt01.size());

    }

    @Test
    void shouldNotSaveOperatorForDuplicateOperatorId() {
        Operator operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        Operator operator01 = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .build();
        operatorRepository.save(operator);
        operatorRepository.save(operator01);

        List<Operator> opt01 = operatorRepository.findAll();
        assertEquals(1, opt01.size());

    }

    @Test
    void shouldNotSaveOperatorWithGroups() {
        List<KhatabookGroup> listOfGroups = new ArrayList<>();
        listOfGroups.add(KhatabookGroup.builder()
                .groupName("Personal")
                .groupDescription("For Personal investments")
                .build());
        listOfGroups.add(KhatabookGroup.builder()
                .groupName("Loan")
                .groupDescription("For Loan")
                .build());
        Operator operator = Operator.builder()
                .operatorId("OPT01")
                .firstName("Amit")
                .lastName("Singh")
                .emailId("amit@gmail.com")
                .createdOn(LocalDateTime.now())
                .groups(listOfGroups)
                .build();

        operatorRepository.save(operator);

        List<Operator> opt01 = operatorRepository.findAll();
        assertEquals(1, opt01.size());
        assertEquals(2, opt01.get(0).getGroups().size());

    }


}