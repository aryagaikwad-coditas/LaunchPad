package com.example.LaunchPad.controller;

import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

}
