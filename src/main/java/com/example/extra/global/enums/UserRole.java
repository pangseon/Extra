package com.example.extra.global.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER(Authority.USER),
    COMPANY(Authority.COMPANY),
    ADMIN(Authority.ADMIN);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {

        public static final String USER = "ROLE_USER";
        public static final String COMPANY = "ROLE_COMPANY";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}