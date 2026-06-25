package com.example.LaunchPad.dto.response;

import com.example.LaunchPad.constants.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnboardingResponse {
    private Long id;

    private Long newHireId;

    private String newHireName;

    private Long managerId;

    private String managerName;

    private Long hrId;

    private String hrName;

    private Long journeyId;

    private String journeyTitle;

    private LocalDate startDate;

    private OnboardingStatus status;
}
