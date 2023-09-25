package com.generic.khatabook.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Containers<T, U> implements Iterable<Container<T, U>> {
    private final List<Container<T, U>> myList;

    public Containers() {
        myList = new ArrayList<>();
    }

    public Containers(final List<Container<T, U>> list) {
        myList = list;
    }

    public static <T, U> Containers<T, U> empty() {
        return new Containers<T, U>(Collections.emptyList());
    }

    public boolean add(Container<T, U> container) {
        return myList.add(container);
    }

    @Override
    public Iterator<Container<T, U>> iterator() {
        return myList.iterator();
    }

    public int size() {
        return myList.size();
    }

}
