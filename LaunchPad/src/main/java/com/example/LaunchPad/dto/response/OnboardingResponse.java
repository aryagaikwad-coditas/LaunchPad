package com.example.LaunchPad.dto.response;

import com.example.LaunchPad.constants.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnboardingResponse {
    private Long id;
    private String newHireName;
    private String managerName;
    private String hrName;
    private String journeyTitle;
    private OnboardingStatus status;
    private LocalDate startDate;
    private List<TaskInstanceResponse> tasks;
}
