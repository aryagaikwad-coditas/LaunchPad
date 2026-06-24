package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.LoginRequest;
import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.AuthResponse;
import com.example.LaunchPad.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User logged in successfully",authService.login(request)));
    }
}
