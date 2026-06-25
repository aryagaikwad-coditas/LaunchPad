package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.TaskRequest;
import com.example.LaunchPad.dto.request.UpdateTaskRequest;
import com.example.LaunchPad.dto.response.TaskResponse;
import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.entity.Task;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.JourneyRepository;
import com.example.LaunchPad.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    private final JourneyRepository journeyRepository;

    @Transactional
    public TaskResponse createTask(@Valid TaskRequest request) {
        Journey journey = journeyRepository.findById(request.getJourneyId())
                .orElseThrow(()->new AppException("Invalid journey where task has to be added"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .journey(journey)
                .dueDaysOffset(request.getDueDaysOffset())
                .requiresApproval(request.isRequiresApproval())
                .build();

        task = taskRepository.save(task);
        return mapToResponse(task);

    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this:: mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new AppException("Invalid task id "+id));

        return  mapToResponse(task);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTasksByJourney(Long id) {
        return taskRepository.findByJourneyId(id).stream()
                .map(this:: mapToResponse)
                .toList().getFirst();
    }

    public TaskResponse updateTask(Long id, @Valid UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new AppException("TASK DOES NOT EXISTS TO BE UPDATED"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDaysOffset(request.getDueDaysOffset());
        task.setRequiresApproval(request.isRequiresApproval());
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new AppException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .journeyId(task.getJourney().getId())
                .dueDaysOffset(task.getDueDaysOffset())
                .requiresApproval(task.isRequiresApproval())
                .build();

    }
}
