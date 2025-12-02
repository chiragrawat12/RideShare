package com.rideshare.userservice.strategy;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.exception.DuplicateRoleException;
import com.rideshare.userservice.factory.RoleBasedUserFactory;
import com.rideshare.userservice.model.Passenger;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerRegistrationStrategy implements RegistrationStrategy {

    private final PassengerRepository passengerRepository;
    private final RoleBasedUserFactory roleFactory;

    @Override
    public void register(User user, RegisterRequest req) {
        if (passengerRepository.existsByUser(user)) {
            throw new DuplicateRoleException(user.getEmail(), "PASSENGER");
        }

        Passenger newPassenger = roleFactory.createPassenger(user);
        passengerRepository.save(newPassenger);
    }
}

