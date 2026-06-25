package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.JourneyRequest;
import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.JourneyResponse;
import com.example.LaunchPad.dto.response.TaskResponse;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.service.JourneyService;
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
@RequestMapping("/api/v1/journeys")
public class JourneyController {

    private final JourneyService journeyService;

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<JourneyResponse >> createJourney(@Valid @RequestBody JourneyRequest request,
                                                                       @AuthenticationPrincipal UserDetails  userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Created The journey",journeyService.createJourney(request,userDetails)));
    }

}
