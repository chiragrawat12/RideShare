package com.rideshare.userservice.strategy;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.exception.DuplicateRoleException;
import com.rideshare.userservice.factory.RoleBasedUserFactory;
import com.rideshare.userservice.model.HostDriver;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.repository.HostDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverRegistrationStrategy implements RegistrationStrategy {

    private final HostDriverRepository hostDriverRepository;
    private final RoleBasedUserFactory roleFactory;

    @Override
    public void register(User user, RegisterRequest req) {
        if (hostDriverRepository.existsByUser(user)) {
            throw new DuplicateRoleException(user.getEmail(), "DRIVER");
        }

        HostDriver newDriver = roleFactory.createDriver(user);
        hostDriverRepository.save(newDriver);
    }
}
