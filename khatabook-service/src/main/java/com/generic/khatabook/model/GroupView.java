package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GroupView(String groupId, String groupName) {
    public static GroupView of(String groupId) {
        return new GroupView(groupId, null);
    }
}
