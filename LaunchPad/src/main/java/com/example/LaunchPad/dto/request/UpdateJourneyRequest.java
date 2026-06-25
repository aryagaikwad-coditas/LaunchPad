package com.example.LaunchPad.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJourneyRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
