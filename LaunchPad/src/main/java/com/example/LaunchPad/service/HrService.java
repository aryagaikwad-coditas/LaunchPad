package com.example.LaunchPad.service;

import com.example.LaunchPad.constants.OnboardingStatus;
import com.example.LaunchPad.constants.Role;
import com.example.LaunchPad.constants.TaskStatus;
import com.example.LaunchPad.dto.request.CreateEmployeeRequest;
import com.example.LaunchPad.dto.response.EmployeeResponse;
import com.example.LaunchPad.entity.*;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HrService {

    private final UserRepository userRepository;
    private final JourneyRepository journeyRepository;
    private final OnboardingRecordRepository onboardingRecordRepository;
    private final TaskRepository taskRepository;
    private final TaskInstanceRepository taskInstanceRepository;
    private final PasswordEncoder  passwordEncoder;
    private final EmailService emailService;

    public EmployeeResponse createEmployee(@Valid CreateEmployeeRequest request, String hrMail) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException("Email already in use");
        }

        Users manager = userRepository.findById(request.getManagerId())
                .orElseThrow(()-> new AppException("Manager not found"));

        if(manager.getRole() != Role.HR){
            throw new AppException("Selected user is not a manager");
        }

        Journey journey = journeyRepository.findById(request.getJourneyId())
                .orElseThrow(()-> new AppException("Journey not found"));

        Users hr = userRepository.findByEmail(hrMail)
                .orElseThrow(()-> new AppException("User not found"));

        List<Task> templateTasks = taskRepository.findByJourneyId(journey.getId());
        if (templateTasks.isEmpty()) {
            throw new AppException("Journey has no tasks. Add tasks before assigning.");
        }

        String tempPassword = UUID.randomUUID().toString().substring(0,8);

        Users employee = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(tempPassword))
                .role(Role.NEW_HIRE)
                .passwordChanged(false)
                .is_active(true)
                .build();

        employee = userRepository.save(employee);

        OnboardingRecord record = OnboardingRecord.builder()
                .newHire(employee)
                .manager(manager)
                .hr(hr)
                .journey(journey)
                .status(OnboardingStatus.IN_PROGRESS)
                .startDate(LocalDate.now())
                .build();

        onboardingRecordRepository.save(record);

        List<TaskInstance> instances = new ArrayList<>();
        for (Task task : templateTasks) {
            instances.add(TaskInstance.builder()
                    .onboardingRecord(record)
                    .task(task)
                    .title(task.getTitle())
                    .taskStatus(TaskStatus.TODO)
                    .dueDate(LocalDate.now().plusDays(task.getDueDaysOffset()))
                    .build());
        }
        taskInstanceRepository.saveAll(instances);

        emailService.sendNewHireWelcomeEmail(
                employee.getEmail(),
                employee.getUsername(),
                tempPassword
        );

        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .email(employee.getEmail())
                .role(employee.getRole().name())
                .build();
    }
}
