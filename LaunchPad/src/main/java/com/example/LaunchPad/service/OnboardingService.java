package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.CreateOnboardingRequest;
import com.example.LaunchPad.dto.response.OnboardingResponse;
import com.example.LaunchPad.entity.OnboardingRecord;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.DocumentRepository;
import com.example.LaunchPad.repository.OnboardingRecordRepository;
import com.example.LaunchPad.repository.TaskInstanceRepository;
import com.example.LaunchPad.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardingService {

    private final TaskInstanceRepository  taskInstanceRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final OnboardingRecordRepository onboardingRecordRepository;

    public OnboardingResponse getMyOnboarding(String username) {

    }

    @Transactional(readOnly = true)
    public List<OnboardingResponse> getAllOnboardings() {
    }


    public List<OnboardingResponse> getManagerOnboardings(String username) {
    }

    public OnboardingResponse getOnboardingById(Long id) {
    }
}
