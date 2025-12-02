package com.rideshare.userservice.strategy;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.User;

public interface RegistrationStrategy {
    void register(User user, RegisterRequest req);
}
