package com.example.LaunchPad.dto.request;

import com.example.LaunchPad.constants.OnboardingStatus;
import jakarta.annotation.security.DenyAll;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOnboardingRequest {
    @NotNull(message = "Status is required Field")
    private OnboardingStatus status;
}
