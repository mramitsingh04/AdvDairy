package com.generic.khatabook.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainerTest {

    @Test
    void testContainerEmpty() {
        final Container<Object, Object> container = Container.empty();
        assertNull(container.get());
        assertNull(container.updatable());
        assertFalse(container.isPresent());
        assertTrue(container.isAbsent());
    }

    @Test
    void testContainerHasValueOnly() {
        final Container<Object, Object> container = Container.of("MyValue");
        assertNotNull(container.get());
        assertNull(container.updatable());
        assertTrue(container.isPresent());
        assertFalse(container.isAbsent());
    }

    @Test
    void testContainerHasUpdatableValueOnly() {
        final Container<Object, Object> container = Container.of("MyValue", new StringBuffer("MyValue"));
        assertNotNull(container.get());
        assertNotNull(container.updatable());
        assertTrue(container.isPresent());
        assertFalse(container.isAbsent());
    }

    @Test
    void testContainerHasNullValueOnly_shouldThrowNullPointerException() {
        assertThrowsExactly(NullPointerException.class, () -> Container.of(null), "Value should not be null");
    }

    @Test
    void testContainerHasNullUpdatableValueOnly_shouldThrowNullPointerException() {
        assertThrowsExactly(NullPointerException.class, () -> Container.of("null", null), "Value should not be null");
    }

    @Test
    void testContainerHasNullValueOnly_shouldNotThrowNullPointerException() {
        assertDoesNotThrow(() -> Container.ofNullable(null), "Value may be null");
    }

    @Test
    void testContainerHasNullUpdatableValueOnly_shouldNotThrowNullPointerException() {
        assertDoesNotThrow(() -> Container.ofNullable("null", null), "Updatable may be null");
    }

}