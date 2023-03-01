package com.example.demo.security.models;

/**
 * Перечисления прав доступа к системе
 * @autor Шаля Андрей
 * @version 2.0
 */
public enum Permission {
    PROJECT_CREATE("projects:create"),
    PROJECT_FIND("project:find"),
    USERS_CREATE("users:create"),
    KICK_STARTER_DONATE("starter:donate"),
    USERS_LIST("users:list");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
