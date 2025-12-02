package com.rideshare.userservice.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class RegistrationStrategyFactory {

    private final DriverRegistrationStrategy driverStrategy;
    private final PassengerRegistrationStrategy passengerStrategy;

    public RegistrationStrategy getStrategy(String role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        switch (role.toUpperCase(Locale.ROOT)) {
            case "DRIVER": return driverStrategy;
            case "PASSENGER": return passengerStrategy;
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
