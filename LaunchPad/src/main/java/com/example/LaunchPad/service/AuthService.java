package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.LoginRequest;
import com.example.LaunchPad.dto.response.AuthResponse;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.UserNotFoundException;
import com.example.LaunchPad.repository.UserRepository;
import com.example.LaunchPad.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse login(@Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found "));

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        return new AuthResponse(token);
    }
}
