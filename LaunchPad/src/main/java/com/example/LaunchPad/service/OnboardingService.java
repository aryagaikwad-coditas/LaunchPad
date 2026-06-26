package com.example.LaunchPad.service;

import com.example.LaunchPad.constants.OnboardingStatus;
import com.example.LaunchPad.constants.Role;
import com.example.LaunchPad.constants.TaskStatus;
import com.example.LaunchPad.dto.request.CreateOnboardingRequest;
import com.example.LaunchPad.dto.response.OnboardingResponse;
import com.example.LaunchPad.entity.*;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardingService {

    private final TaskInstanceRepository  taskInstanceRepository;
    private final TaskRepository taskRepository;
    private final JourneyRepository  journeyRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
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

    public OnboardingResponse createOnboarding(@Valid CreateOnboardingRequest request, String hrMail) {
        Users newHire = userRepository.findById(request.getNewHireId())
                .orElseThrow(()-> new AppException("User not found"));

        if(newHire.getRole() != Role.NEW_HIRE){
            throw new AppException("Selected user is not a new hire role");
        }

        Users manager = userRepository.findById(request.getManagerId())
                .orElseThrow(()-> new AppException("Manager not found"));

        if(manager.getRole() != Role.MANAGER){
            throw new AppException("Selected user is not a manager role");
        }
        Users hr = userRepository.findByEmail(hrMail)
                .orElseThrow(()-> new AppException("Hr not found "));

        Journey journey =journeyRepository.findById(request.getJourneyId())
                .orElseThrow(()-> new AppException("Journey not found"));

        List<Task> templateTasks = taskRepository.findByJourneyId(journey.getId());
        if (templateTasks.isEmpty()) {
            throw new AppException("Journey has no tasks");
        }
        OnboardingRecord record =  OnboardingRecord.builder()
                .newHire(newHire)
                .manager(manager)
                .hr(hr)
                .journey(journey)
                .startDate(LocalDate.now())
                .status(OnboardingStatus.IN_PROGRESS)
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
        return toResponse(record);
    }

    private OnboardingResponse toResponse(OnboardingRecord record) {
    }

    public OnboardingResponse markCompleted(Long onboardingId) {
    }

    public List<OnboardingResponse> getMyTeam(String username) {
    }

    public OnboardingResponse getMyOnboarding(String username) {
    }
}
