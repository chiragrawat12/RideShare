package com.rideshare.userservice.service;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.exception.EmailAlreadyUsedException;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.repository.UserRepository;
import com.rideshare.userservice.strategy.RegistrationStrategy;
import com.rideshare.userservice.strategy.RegistrationStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RegistrationStrategyFactory strategyFactory;

    public User register(RegisterRequest req) {

        // 1. Check if a user already exists with this email
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

        User user;
        if (optionalUser.isEmpty()) {
            // 2. New email → create base User
            user = new User();
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPhone(req.getPhone());
            user.setPassword(req.getPassword());
            user.setVerified(false);
            user = userRepository.save(user);
        } else {
            user = optionalUser.get();

            // 3. Same email but different person → Block
            boolean samePerson =
                    safeEquals(user.getName(), req.getName()) &&
                            safeEquals(user.getPhone(), req.getPhone()) &&
                            safeEquals(user.getPassword(), req.getPassword());

            if (!samePerson) {
                throw new EmailAlreadyUsedException(req.getEmail());
            }
        }

        // 4. Strategy Pattern for role registration
        RegistrationStrategy strategy = strategyFactory.getStrategy(req.getRole());
        strategy.register(user, req);

        // 5. Return saved/updated user
        return user;
    }

    private boolean safeEquals(String a, String b) {
        if (a == null) return b == null;
        return a.equals(b);
    }
}

