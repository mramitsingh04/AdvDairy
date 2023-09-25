package com.generic.khatabook.common.model;

import static java.util.Objects.requireNonNull;

public record Container<T, U>(T value, U updatable) {

    private static final Container<?, ?> EMPTY = new Container<>(null, null);

    public static <T, U> Container<T, U> empty() {
        @SuppressWarnings("unchecked") Container<T, U> t = (Container<T, U>) EMPTY;
        return t;
    }

    public static <T, U> Container<T, U> of(T value) {
        return new Container<>(requireNonNull(value), null);
    }


    public static <T, U> Container<T, U> ofNullable(T value) {
        return value == null ? Container.empty() : new Container<>(value, null);
    }


    public static <T, U> Container<T, U> of(T value, U updatable) {
        return new Container<>(requireNonNull(value), requireNonNull(updatable));
    }


    public static <T, U> Container<T, U> ofNullable(T value, U updatable) {
        return value == null || updatable == null ? Container.empty() : new Container<>(value, updatable);
    }


    public T get() {
        return value;
    }

    @Override
    public U updatable() {
        return updatable;
    }

    public boolean isAbsent() {
        return !isPresent();
    }

    public boolean isPresent() {
        return value != null || updatable != null;
    }
}
