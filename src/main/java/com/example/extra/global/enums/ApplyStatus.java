package com.example.extra.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ApplyStatus {
    APPLIED,
    REJECTED,
    APPROVED;

    @JsonCreator
    public static ApplyStatus fromString(String inputValue) {
        return EnumUtils.fromString(ApplyStatus.class, inputValue);
    }
}
