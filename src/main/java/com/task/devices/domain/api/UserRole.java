package com.task.devices.domain.api;

public enum UserRole {
    CLIENT,
    BACKOFFICE;

    public boolean isAdmin() {
        return this == BACKOFFICE;
    }
}
