package com.rideshare.userservice.exception;

import lombok.Getter;

@Getter  // 🔹 Lombok generates getRole() and getEmail()
public class DuplicateRoleException extends RuntimeException {

    private final String email;
    private final String role;

    public DuplicateRoleException(String email, String role) {
        super("You are already registered as " + role);
        this.email = email;
        this.role = role;
    }
}
