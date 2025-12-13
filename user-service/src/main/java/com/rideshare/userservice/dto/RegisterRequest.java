package com.rideshare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;   // "DRIVER" or "PASSENGER"

    // OPTIONAL – only used if role == DRIVER
    private String licenseNumber;
    private String vehicleDetails;

    // OPTIONAL – only used if role == PASSENGER
    private String preferredPaymentMethod;
}
