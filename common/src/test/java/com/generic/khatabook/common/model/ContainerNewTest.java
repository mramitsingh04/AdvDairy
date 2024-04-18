package com.generic.khatabook.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerNewTest {

    @Test
    void emptyContainerShouldHaveNullValues() {
        final Container<Object, Object> container = Container.empty();
        assertNull(container.get());
        assertNull(container.updatable());
    }

    @Test
    void containerWithValueShouldHaveNonNullValue() {
        final Container<Object, Object> container = Container.of("MyValue");
        assertNotNull(container.get());
        assertNull(container.updatable());
    }

    @Test
    void containerWithUpdatableValueShouldHaveNonNullValues() {
        final Container<Object, Object> container = Container.of("MyValue", new StringBuffer("MyValue"));
        assertNotNull(container.get());
        assertNotNull(container.updatable());
    }

    @Test
    void containerWithNullValueShouldThrowException() {
        assertThrows(NullPointerException.class, () -> Container.of(null));
    }

    @Test
    void containerWithNullUpdatableValueShouldThrowException() {
        assertThrows(NullPointerException.class, () -> Container.of("null", null));
    }

    @Test
    void containerWithNullableNullValueShouldNotThrowException() {
        assertDoesNotThrow(() -> Container.ofNullable(null));
    }

    @Test
    void containerWithNullableNullUpdatableValueShouldNotThrowException() {
        assertDoesNotThrow(() -> Container.ofNullable("null", null));
    }

    @Test
    void containerShouldBeAbsentIfValuesAreNull() {
        final Container<Object, Object> container = Container.empty();
        assertTrue(container.isAbsent());
    }

    @Test
    void containerShouldBePresentIfValuesAreNonNull() {
        final Container<Object, Object> container = Container.of("MyValue", new StringBuffer("MyValue"));
        assertTrue(container.isPresent());
    }
}