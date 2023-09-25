package com.generic.khatabook.controller;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LocalTesting {
    @Test
    void test() {
        List<String> listOfString = new ArrayList<>();
        listOfString.add("A");
        listOfString.add("B");
        listOfString.add("C");
        listOfString.add("D");
        listOfString.add("E");
        listOfString.add(null);

        List<String> listOfString1 = new ArrayList<>();
        listOfString1.add("D");
        listOfString.retainAll(listOfString1);
        System.out.println(listOfString);


    }
}
