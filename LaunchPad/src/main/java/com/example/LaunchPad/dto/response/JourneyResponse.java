package com.example.LaunchPad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JourneyResponse {
    private Long id;
    private String title;
    private String description;
    private String createdByHrName;
}
