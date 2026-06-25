package com.example.LaunchPad.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private boolean requiresApproval;
    private Integer dueDaysOffset;
    private Long journeyId;
}
