package com.generic.khatabook.common.model;

public sealed interface Updatable<T> permits DefaultUpdatable {
    default T updatable() {
        return null;
    }
}
