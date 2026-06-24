package com.example.LaunchPad.entity;

import com.example.LaunchPad.constants.DocumentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_instance_id",nullable = false)
    private TaskInstance taskInstance;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users newHire;

    private String fileName;

    private String fileType;

    private String filePath;

    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    private String rejectionReason;

    private LocalDateTime uploadedAt;
}
