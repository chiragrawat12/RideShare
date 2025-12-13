package com.rideshare.userservice.service;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.repository.UserRepository;
import com.rideshare.userservice.security.JwtUtil;
import com.rideshare.userservice.strategy.RegistrationStrategy;
import com.rideshare.userservice.strategy.RegistrationStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegistrationStrategyFactory strategyFactory;

    @Mock
    private RegistrationStrategy registrationStrategy;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterNewUser() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Ganesh");
        request.setEmail("ganesh@example.com");
        request.setPhone("9999999999");
        request.setPassword("password");
        request.setRole("PASSENGER");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(strategyFactory.getStrategy("PASSENGER"))
                .thenReturn(registrationStrategy);

        userService.register(request);
    }

    @Test
    void testLoginWithInvalidPassword() {

        User user = new User();
        user.setEmail("ganesh@example.com");
        user.setPassword("encoded-password");

        when(userRepository.findByEmail("ganesh@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThrows(RuntimeException.class, () ->
                userService.login("ganesh@example.com", "wrong-password"));
    }
}
