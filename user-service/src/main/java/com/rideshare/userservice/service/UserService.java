package com.rideshare.userservice.service;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.*;
import com.rideshare.userservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.rideshare.userservice.repository.HostDriverRepository;
import com.rideshare.userservice.repository.PassengerRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HostDriverRepository hostDriverRepository;
    private final PassengerRepository passengerRepository;

    public Object register(RegisterRequest req) {

        // Check if user exists
        User user = userRepository.findByEmail(req.getEmail()).orElse(null);

        if (user == null) {
            user = new User();
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPhone(req.getPhone());
            user.setPassword(req.getPassword());
            user = userRepository.save(user);  // Save main user
        }

        // Now role-based insertion
        if (req.getRole().equalsIgnoreCase("DRIVER")) {
            if (!hostDriverRepository.existsByUser(user)) {
                HostDriver d = new HostDriver();
                d.setUser(user);
                hostDriverRepository.save(d);
            }
        }

        if (req.getRole().equalsIgnoreCase("PASSENGER")) {
            if (!passengerRepository.existsByUser(user)) {
                Passenger p = new Passenger();
                p.setUser(user);
                passengerRepository.save(p);
            }
        }

        return user;
    }
}

