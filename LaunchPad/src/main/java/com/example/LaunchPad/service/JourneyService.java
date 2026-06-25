package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.JourneyRequest;
import com.example.LaunchPad.dto.response.JourneyResponse;
import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.repository.JourneyRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JourneyService {
    
    private final JourneyRepository journeyRepository;

    public JourneyResponse createJourney(@Valid JourneyRequest request) {
        Journey journey = Journey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        return mapToResponse(journeyRepository.save(journey));
    }


    private JourneyResponse mapToResponse(Journey j) {
        return JourneyResponse.builder()
                .title(j.getTitle())
                .description(j.getDescription())
                .id(j.getId())
                .createdByHrName(j.getCreatedByHr().getUsername())
                .build();
    }

}
