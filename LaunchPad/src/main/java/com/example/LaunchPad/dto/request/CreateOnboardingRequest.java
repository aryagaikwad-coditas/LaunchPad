package com.example.LaunchPad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOnboardingRequest {
    private Long managerId;
    private Long journeyId;
    private Long newHireId;
    private Long hrId;
}
