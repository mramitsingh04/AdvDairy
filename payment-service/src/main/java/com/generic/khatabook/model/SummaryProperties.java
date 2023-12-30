package com.generic.khatabook.model;

public record SummaryProperties(String sorting, String sortingBy, int limit) {


    public static SummaryProperties non() {
        return new SummaryProperties(null, null, -1);
    }

    public static SummaryProperties of(final String sorting, final String sortingBy) {
        return new SummaryProperties(sorting, sortingBy, Integer.MAX_VALUE);
    }
}
