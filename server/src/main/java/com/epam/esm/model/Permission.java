package com.epam.esm.model;

/**
 * The class with users permissions
 */
public enum Permission {

    READ("read"),
    WRITE("write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
