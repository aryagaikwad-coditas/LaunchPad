package com.example.LaunchPad.service;

import com.example.LaunchPad.constants.OnboardingStatus;
import com.example.LaunchPad.constants.TaskStatus;
import com.example.LaunchPad.dto.response.TaskInstanceResponse;
import com.example.LaunchPad.entity.OnboardingRecord;
import com.example.LaunchPad.entity.TaskInstance;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.OnboardingRecordRepository;
import com.example.LaunchPad.repository.TaskInstanceRepository;
import com.example.LaunchPad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskInstanceService {

    private final TaskInstanceRepository taskInstanceRepository;
    private final OnboardingRecordRepository onboardingRecordRepository;
    private final EmailService  emailService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TaskInstanceResponse> getTaskByOnboardings(Long onboardingId) {
        return taskInstanceRepository.findByOnboardingRecordId(onboardingId)
                .stream()
                .map(this:: toResponse)
                .toList();

    }

    public TaskInstanceResponse completeTask(Long taskInstanceId, String email) {
        Users newHire = userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("User not found "));

        TaskInstance instance = taskInstanceRepository.findById(taskInstanceId)
                .orElseThrow(()-> new AppException("TaskInstance not found"));

        if(!instance.getOnboardingRecord().getNewHire().getId().equals(newHire.getId())){
            throw new AppException("Selected user is not allowed access to the system");
        }

        if(instance.getTaskStatus() == TaskStatus.COMPLETED){
            throw new AppException("Task is already completed");
        }
        instance.setTaskStatus(TaskStatus.IN_PROGRESS);
        instance.setCompletedAt(LocalDateTime.now());
        taskInstanceRepository.save(instance);

        return toResponse(instance);
    }

    public TaskInstanceResponse approveTask(Long taskInstanceId, String email) {
        Users manager = userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("Manager not found "));

        TaskInstance instance = taskInstanceRepository.findById(taskInstanceId)
                .orElseThrow(()-> new AppException("TaskInstance not found"));

        if(!instance.getOnboardingRecord().getManager().getId().equals(manager.getId())){
            throw new AppException("Selected user is not allowed access to the system");
        }

        if(instance.getTaskStatus() == TaskStatus.COMPLETED){
            throw new AppException("Task has already been completed");
        }

        if(instance.getTaskStatus() == TaskStatus.IN_PROGRESS){
            throw new AppException("First ask the new hire to submit the task");
        }
        instance.setTaskStatus(TaskStatus.COMPLETED);
        taskInstanceRepository.save(instance);
        Users newHire = instance.getOnboardingRecord().getNewHire();
        emailService.sendTaskApprovedEmail(newHire.getEmail(), newHire.getUsername(), instance.getTitle());

        checkAndCompleteOnboarding(instance.getOnboardingRecord());
        return toResponse(instance);
    }


    public TaskInstanceResponse rejectTask(Long taskInstanceId, String reason, String managerEmail) {
        if(reason == null || reason.isEmpty()){
            throw new AppException("Rejected Because Reason is Required");
        }
        Users manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(()-> new AppException("Manager not found"));

        TaskInstance instance = taskInstanceRepository.findById(taskInstanceId)
                .orElseThrow(()-> new AppException("TaskInstance not found"));

        if(!instance.getOnboardingRecord().getManager().getId().equals(manager.getId())){
            throw new AppException("Selected user is not allowed access to the system");
            // if the manager is not the one who is briefing the system
        }
        if(instance.getTaskStatus() == TaskStatus.COMPLETED){
            throw new AppException("Task has already been approved cannot be rejected");
        }
        instance.setTaskStatus(TaskStatus.REJECTED);
        taskInstanceRepository.save(instance);

        Users newHire = instance.getOnboardingRecord().getNewHire();
        emailService.sendTaskRejectedEmail(newHire.getEmail(),
                newHire.getUsername(),
                instance.getTitle(),
                reason);
        return toResponse(instance);
    }

    private TaskInstanceResponse toResponse(TaskInstance taskInstance) {
        return TaskInstanceResponse.builder()
                .id(taskInstance.getId())
                .title(taskInstance.getTitle())
                .dueDate(taskInstance.getDueDate())
                .taskStatus(taskInstance.getTaskStatus())
                .requiresApproval(taskInstance.getTask().isRequiresApproval())
                .build();
    }

    private void checkAndCompleteOnboarding(OnboardingRecord onboardingRecord) {
        long remaining = taskInstanceRepository
                .countByOnboardingRecordIdAndTaskStatusNot(onboardingRecord.getId(),TaskStatus.COMPLETED);


        if(remaining > 0){
            onboardingRecord.setStatus(OnboardingStatus.COMPLETED);
            onboardingRecordRepository.save(onboardingRecord);
            emailService.sendOnboardingCompleteEmail(onboardingRecord.getNewHire().getEmail(),
                    onboardingRecord.getNewHire().getUsername());
        }


    }
}
