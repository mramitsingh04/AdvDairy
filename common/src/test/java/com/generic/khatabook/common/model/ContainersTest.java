package com.generic.khatabook.common.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.generic.khatabook.common.model.Containers.empty;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainersTest {

    @Test
    void testContainers() {
        assertDoesNotThrow(() -> empty());
    }
    @Test
    void testContainersValue(){
        List<Container<String, String>> listOfContainer = new ArrayList<>();
        listOfContainer.add(Container.of("Amit", "Singh"));
        listOfContainer.add(Container.of("Amit", "Singh"));
        listOfContainer.add(Container.of("Amit", "Singh"));
        Containers<String, String> containers = new Containers<>(listOfContainer);
        assertEquals(3, containers.size());
        assertTrue(containers.iterator().hasNext());
        assertEquals("Amit", containers.iterator().next().get());
    }

}