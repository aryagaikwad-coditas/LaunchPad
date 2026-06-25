package com.example.LaunchPad.service;

import com.example.LaunchPad.constants.OnboardingStatus;
import com.example.LaunchPad.constants.Role;
import com.example.LaunchPad.dto.request.CreateEmployeeRequest;
import com.example.LaunchPad.dto.response.EmployeeResponse;
import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.entity.OnboardingRecord;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

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

    public EmployeeResponse createEmployee(CreateEmployeeRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException("Email already exists");
        }

        Users hr = userRepository.findById(request.getHrId())
                .orElseThrow(() -> new AppException("Hr not found"));

        Users manager = userRepository.findById(request.getManagerId())
                .orElseThrow(()-> new AppException("Manager not found with this Id"));

        Journey journey = journeyRepository.findById(request.getJourneyId())
                .orElseThrow(()-> new AppException("Journey Not Found with This Id"));

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        Users employee = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(tempPassword))
                .role(Role.NEW_HIRE)
                .is_active(true)
                .build();

        employee = userRepository.save(employee);

        OnboardingRecord onboardingRecord = OnboardingRecord.builder()
                .newHire(employee)
                .manager(manager)
                .journey(journey)
                .startDate(LocalDate.now())
                .status(OnboardingStatus.IN_PROGRESS)
                .hr(hr)
                .build();

        onboardingRecordRepository.save(onboardingRecord);
        emailService.sendNewHireWelcomeMail(employee.getEmail(), tempPassword);

        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .email(employee.getEmail())
                .role(employee.getRole().name())
                .build();
    }
}
