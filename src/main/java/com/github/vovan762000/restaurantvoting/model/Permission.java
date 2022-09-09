package com.github.vovan762000.restaurantvoting.model;

import lombok.Getter;

@Getter
public enum Permission {

    SCOPE_USER_PERMISSION("scope:user_permission"),
    SCOPE_ADMIN_PERMISSION("scope:admin_permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

}
