package com.rideshare.userservice.controller;

import com.rideshare.userservice.dto.LoginRequest;
import com.rideshare.userservice.dto.LoginResponse;
import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User user = userService.register(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "status", "success",
                        "message", "User registered successfully as " + req.getRole(),
                        "userId", user.getId(),
                        "email", user.getEmail()
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        String token = userService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

}

