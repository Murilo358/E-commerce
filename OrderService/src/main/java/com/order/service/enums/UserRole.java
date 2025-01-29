package com.order.service.enums;

public enum UserRole {
    SELLER("seller"),
    BUYER("buyer");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

