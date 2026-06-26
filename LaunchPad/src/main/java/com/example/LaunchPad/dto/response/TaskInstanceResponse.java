package com.example.LaunchPad.dto.response;

import com.example.LaunchPad.constants.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskInstanceResponse {
    private Long id;
    private String title;
    private LocalDate dueDate;
    private TaskStatus taskStatus;
    private boolean requiresApproval;
    private LocalDateTime completedAt;
}
