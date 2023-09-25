package com.generic.khatabook.common.model;

public final class DefaultUpdatable implements Updatable {
    @Override
    public Object updatable() {
        return Updatable.super.updatable();
    }
}
