package com.example.extra.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    ROMANCE,
    COMEDY,
    DRAMA,
    THRILLER,
    HORROR,
    MYSTERY,
    FANTASY,
    SF,
    HISTORICAL,
    ACTION,
    ADVENTURE,
    CRIME,
    WAR,
    FAMILY,
    MELODRAMA,
    MUSICAL,
    SPORTS,
    LEGAL,
    MEDICAL,
    POLITICAL;
    @JsonCreator
    public static Category fromString(String inputValue) {
        return EnumUtils.fromString(Category.class, inputValue);
    }
}
