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


    @Transactional(readOnly = true)
    public List<OnboardingResponse> getAllOnboardings() {
        return onboardingRecordRepository.findAll()
                .stream()
                .map(this :: map)
                .toList();
    }

    private OnboardingResponse map(OnboardingRecord onboardingRecord) {
        return OnboardingResponse.builder()
                .id(onboardingRecord.getId())
                .newHireId(onboardingRecord.getNewHire().getId())
                .newHireName(onboardingRecord.getNewHire().getUsername())
                .managerId(onboardingRecord.getManager().getId())
                .managerName(onboardingRecord.getManager().getUsername())
                .journeyId(onboardingRecord.getJourney().getId())
                .journeyTitle(onboardingRecord.getJourney().getTitle())
                .startDate(onboardingRecord.getStartDate())
                .status(onboardingRecord.getStatus())
                .build();
    }

    public OnboardingResponse getOnboardingById(Long id) {
        OnboardingRecord onboardingRecord = onboardingRecordRepository.findById(id)
                .orElseThrow(()-> new AppException("OnboardingRecord not found"));

        onboardingRecord = onboardingRecordRepository.save(onboardingRecord);
        return map(onboardingRecord);
    }

}
