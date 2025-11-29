package com.rideshare.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;   // DRIVER or PASSENGER
}
