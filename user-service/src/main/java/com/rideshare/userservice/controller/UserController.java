package com.rideshare.userservice.controller;

import com.rideshare.userservice.dto.RegisterRequest;
import com.rideshare.userservice.model.User;
import com.rideshare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(userService.register(req));
    }
}
