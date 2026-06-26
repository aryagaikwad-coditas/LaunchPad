package com.example.LaunchPad.service;

import com.example.LaunchPad.constants.OnboardingStatus;
import com.example.LaunchPad.constants.Role;
import com.example.LaunchPad.constants.TaskStatus;
import com.example.LaunchPad.dto.request.CreateOnboardingRequest;
import com.example.LaunchPad.dto.response.OnboardingResponse;
import com.example.LaunchPad.dto.response.TaskInstanceResponse;
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

    public OnboardingResponse markCompleted(Long id) {
        OnboardingRecord record = onboardingRecordRepository.findById(id)
                .orElseThrow(()-> new AppException("Record not found"));

        if(record.getStatus() == OnboardingStatus.COMPLETED){
            throw new AppException("Onboarding has been completed");
        }
        record.setStatus(OnboardingStatus.COMPLETED);
        onboardingRecordRepository.save(record);

        emailService.sendOnboardingCompleteEmail(record.getNewHire().getEmail(),record.getNewHire().getUsername());

        return toResponse(record);
    }

    @Transactional(readOnly = true)
    public List<OnboardingResponse> getMyTeam(String managerEmail) {
            Users manager = userRepository.findByEmail(managerEmail)
                    .orElseThrow(()-> new AppException("Manager not found"));

            return onboardingRecordRepository.findByManagerId(manager.getId())
                    .stream()
                    .map(this::toResponse)
                    .toList();

    }

    public OnboardingResponse getMyOnboarding(String email) {
        Users newHire = userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("Employee not found"));

        OnboardingRecord record = onboardingRecordRepository.findByNewHireId(newHire.getId())
                .orElseThrow(()-> new AppException("Record not found"));

        return  toResponse(record);

    }

    @Transactional(readOnly = true)
    public List<OnboardingResponse> getAllOnboardings() {
        return onboardingRecordRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OnboardingResponse getOnboardingById(Long id, String email) {

        Users user = userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("User not found"));

        OnboardingRecord record = onboardingRecordRepository.findById(id)
                .orElseThrow(()-> new AppException("Onboarding record not found"));

        if(user.getRole() == Role.MANAGER && !record.getManager().getId().equals(user.getId())) {
            throw new AppException("Not authorized to view this record");
        }

        if(user.getRole() == Role.NEW_HIRE && !record.getNewHire().getId().equals(user.getId())){
            throw new AppException("Not authorized to view this record");
        }

        return toResponse(record);
    }

    private OnboardingResponse toResponse(OnboardingRecord record) {
        List<TaskInstanceResponse> tasks = taskInstanceRepository
                .findByOnboardingRecordId(record.getId())
                .stream()
                .map(i-> TaskInstanceResponse
                        .builder()
                        .id(i.getId())
                        .title(i.getTitle())
                        .dueDate(i.getDueDate())
                        .taskStatus(i.getTaskStatus())
                        .requiresApproval(i.getTask().isRequiresApproval())
                        .build())
                .toList();

        return OnboardingResponse.builder()
                .id(record.getId())
                .newHireName(record.getNewHire().getUsername())
                .managerName(record.getManager().getUsername())
                .hrName(record.getManager().getUsername())
                .journeyTitle(record.getJourney().getTitle())
                .status(record.getStatus())
                .startDate(record.getStartDate())
                .tasks(tasks)
                .build();
    }

}
