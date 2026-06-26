package com.example.LaunchPad.dto.response;

import com.example.LaunchPad.constants.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private DocumentStatus documentStatus;
    private String rejectionReason;
    private LocalDateTime uploadedAt;
    private Long taskInstanceId;
}
