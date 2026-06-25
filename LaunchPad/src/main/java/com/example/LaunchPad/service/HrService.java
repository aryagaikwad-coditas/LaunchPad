package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.CreateEmployeeRequest;
import com.example.LaunchPad.dto.response.EmployeeResponse;
import com.example.LaunchPad.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HrService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskInstanceRepository taskInstanceRepository;
    private final JourneyRepository journeyRepository;
    private final OnboardingRecordRepository  onboardingRecordRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
}
