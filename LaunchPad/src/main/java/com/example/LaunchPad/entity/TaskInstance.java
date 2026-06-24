package com.example.LaunchPad.entity;

import com.example.LaunchPad.constants.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="task_instances")
public class TaskInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "onboarded_record_id")
    private OnboardingRecord onboardingRecord;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String title;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDateTime completedAt;
}
