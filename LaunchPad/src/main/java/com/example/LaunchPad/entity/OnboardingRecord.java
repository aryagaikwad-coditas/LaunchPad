package com.example.LaunchPad.entity;

import com.example.LaunchPad.constants.OnboardingStatus;
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
@Table(name = "onboarding_record")
public class OnboardingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="newHire_id")
    private Users newHire;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users manager;

    @ManyToOne
    @JoinColumn(name = "hr_id")
    private Users hr;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnboardingStatus status;

    @ManyToOne
    @JoinColumn(name = "journey_id")
    private Journey journey;
}
