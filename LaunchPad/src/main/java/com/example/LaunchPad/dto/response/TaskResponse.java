package com.example.LaunchPad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean requiresApproval;
    private Long journeyId;
    private Integer dueDaysOffset;
}
