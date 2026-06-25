package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.JourneyRequest;
import com.example.LaunchPad.dto.response.JourneyResponse;
import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.JourneyRepository;
import com.example.LaunchPad.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JourneyService {
    
    private final JourneyRepository journeyRepository;

    private final UserRepository userRepository;

    public JourneyResponse createJourney(@Valid JourneyRequest request, String username) {

        Users hr  = userRepository.findByEmail(username)
                .orElseThrow(()-> new AppException("User not found "));

        Journey journey = Journey.builder()
                .title(request.getTitle())
                .createdByHr(hr)
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
