package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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