package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.TaskRequest;
import com.example.LaunchPad.dto.request.UpdateTaskRequest;
import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.TaskResponse;
import com.example.LaunchPad.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest request){
        return ResponseEntity.ok(ApiResponse.success("Created The task for the following ",taskService.createTask(request)));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success("Fetched the task with the given Id Successfully",taskService.getTaskById(id)));
    }

    @GetMapping("/journey/{journeyId}")
    public ResponseEntity<List<TaskResponse>> getTaskByJourneyId(@PathVariable Long id){
        return ResponseEntity.ok(Collections.singletonList(taskService.getTaskById(id)));
    }



    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable Long id ,
                                                                @Valid @RequestBody UpdateTaskRequest request){
        return ResponseEntity.ok(ApiResponse.success("Updated the task",taskService.updateTask(id,request)));
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task has been deleted successfully");
    }
}



