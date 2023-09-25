package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"

}, locations = "classpath:application-integrationtest.properties")
class RatingRepositoryTest {

    @Autowired
    RatingRepository repository;

    @Test
    void saveSpecification() {
        final Rating savedSpecification = repository.save(Rating.builder().id(UUID.randomUUID().toString()).build());
        assertNotNull(savedSpecification);
        assertNotNull(savedSpecification.getId());

    }
}