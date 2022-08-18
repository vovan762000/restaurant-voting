package com.github.vovan762000.restaurantvoting.model;

import lombok.Getter;

@Getter
public enum Permission {

    SCOPE_READ("scope:read"),
    SCOPE_WRITE("scope:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

}
