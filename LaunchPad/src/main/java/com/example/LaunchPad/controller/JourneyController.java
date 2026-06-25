package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.JourneyRequest;
import com.example.LaunchPad.dto.request.UpdateJourneyRequest;
import com.example.LaunchPad.dto.response.ApiResponse;
import com.example.LaunchPad.dto.response.JourneyResponse;
import com.example.LaunchPad.dto.response.TaskResponse;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.service.CustomUserDetailsService;
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
    public ResponseEntity<ApiResponse<JourneyResponse>> createJourney(@Valid @RequestBody JourneyRequest request,
                                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Created The journey",journeyService.createJourney(request, userDetails.getUsername())));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<JourneyResponse>> getAllJourneys(){
        return ResponseEntity.ok(journeyService.getAllJourneys());
    }

    @GetMapping("/{journeyId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<JourneyResponse>> getByJourneyId(@PathVariable Long journeyId){
        return ResponseEntity.ok(ApiResponse.success("Fetching journey By their Id",journeyService.getByJourneyId(journeyId)));
    }

    @PutMapping("/{journeyId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<ApiResponse<JourneyResponse>> updateJourney(@PathVariable Long journeyId, @Valid @RequestBody UpdateJourneyRequest request){
        return ResponseEntity.ok(ApiResponse.success("Successfully updated a journey",journeyService.updateJourney(journeyId,request)));
    }

    @DeleteMapping("/{journeyId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<String> deleteJourney(@PathVariable Long journeyId){
        journeyService.deleteJourney(journeyId);
        return ResponseEntity.ok("Successfully deleted the journey");
    }
}
