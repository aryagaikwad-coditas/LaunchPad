package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.CreateOnboardingRequest;
import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.OnboardingResponse;
import com.example.LaunchPad.service.OnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/onboardings")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> createOnboarding(@Valid @RequestBody CreateOnboardingRequest request,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Successfully created an onboarding record",onboardingService.createOnboarding(request,userDetails.getUsername())));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
   public ResponseEntity<ApiResponse<List<OnboardingResponse>>> getAllOnboardings(){
        return ResponseEntity.ok(ApiResponse.success("All Onboarding",onboardingService.getAllOnboardings()));

    }

    @GetMapping("/{onboardingId}")
    @PreAuthorize("hasAnyRole('HR','MANAGER','NEW_HIRE')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> getById(
            @PathVariable Long onboardingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Onboarding record",
                onboardingService.getOnboardingById(onboardingId, userDetails.getUsername())));
    }

    @PutMapping("/{onboardingId}/complete")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> markCompleted(@PathVariable Long onboardingId) {
        return ResponseEntity.ok(ApiResponse.success("Onboarding marked as completed",onboardingService.markCompleted(onboardingId)));
    }

    @GetMapping("/my-team")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<OnboardingResponse>>> getMyTeam(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("My team",
                onboardingService.getMyTeam(userDetails.getUsername())));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('NEW_HIRE')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> getMyOnboarding(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("My onboarding",
                onboardingService.getMyOnboarding(userDetails.getUsername())));
    }
}
