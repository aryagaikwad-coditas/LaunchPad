package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.TaskInstanceResponse;
import com.example.LaunchPad.service.TaskInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/taskInstances")
@RequiredArgsConstructor
public class TaskInstanceController {

    private final TaskInstanceService taskInstanceService;

    @GetMapping("/onboarding/{onboardingId}")
    @PreAuthorize("hasAnyRole('HR','MANAGER')")
    public ResponseEntity<ApiResponse<List<TaskInstanceResponse>>> getTaskByOnboarding(@PathVariable  Long onboardingId){
        return ResponseEntity.ok(ApiResponse.success("Tasks Fetched By Onboarding", taskInstanceService.getTaskByOnboardings(onboardingId)));
    }

    @PutMapping("/{taskInstanceId}/complete")
    @PreAuthorize("hasRole('NEW_HIRE')")
    public ResponseEntity<ApiResponse<TaskInstanceResponse>> completeTask(
            @PathVariable Long taskInstanceId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Task submitted",
                taskInstanceService.completeTask(taskInstanceId, userDetails.getUsername())));
    }


    @PutMapping("/{taskInstanceId}/approve")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<TaskInstanceResponse>> approveTask(
            @PathVariable Long taskInstanceId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Task approved",
                taskInstanceService.approveTask(taskInstanceId, userDetails.getUsername())));
    }


    @PutMapping("/{taskInstanceId}/reject")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<TaskInstanceResponse>> rejectTask(@PathVariable Long taskInstanceId,
                                                                        @RequestParam String reason,
                                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Rejected Task ",taskInstanceService.rejectTask(taskInstanceId,reason,userDetails.getUsername())));
    }
}
