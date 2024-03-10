package com.seanrogandev.bebelink.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    USER_FREE_READ("admin:read"),
    USER_FREE_UPDATE("admin:update"),
    USER_FREE_CREATE("admin:create"),
    USER_FREE_DELETE("admin:delete"),
    USER_PAID_READ("management:read"),
    USER_PAID_UPDATE("management:update"),
    USER_PAID_CREATE("management:create"),
    USER_PAID_DELETE("management:delete");

    @Getter
    private final String permission;
}
