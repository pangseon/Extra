package com.example.extra.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    DRAMA,
    MOVIE,
    ADVERTISEMENT,
    MUSIC_VIDEO,
    ETC;

    @JsonCreator
    public static Category fromString(String inputValue) {
        return EnumUtils.fromString(Category.class, inputValue);
    }
}
