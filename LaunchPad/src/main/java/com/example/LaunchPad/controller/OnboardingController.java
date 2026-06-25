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


    @GetMapping("/me")
    @PreAuthorize("hasRole('NEW_HIRE')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> getMyOnboardings(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Fetching all the onboarding details",onboardingService.getMyOnboarding(userDetails.getUsername())));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<OnboardingResponse>> getAllOnboardings(){
        return ResponseEntity.ok(onboardingService.getAllOnboardings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<OnboardingResponse>> getOnboardingById(@PathVariable Long id ){
        return ResponseEntity.ok(ApiResponse.success("Fetching Onboarding based on Id",onboardingService. getOnboardingById(id)));
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<OnboardingResponse>> getManagerOnboardings(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(onboardingService.getManagerOnboardings(userDetails.getUsername()));
    }
}
