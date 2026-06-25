package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.JourneyRequest;
import com.example.LaunchPad.dto.request.UpdateJourneyRequest;
import com.example.LaunchPad.dto.response.JourneyResponse;
import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.JourneyRepository;
import com.example.LaunchPad.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JourneyService {
    
    private final JourneyRepository journeyRepository;

    private final UserRepository userRepository;

    public JourneyResponse createJourney(@Valid JourneyRequest request, String username) {
        Users hr = userRepository.findByEmail(username)
                .orElseThrow(()-> new AppException("Hr does not exists with this Id"));

        Journey journey = Journey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdByHr(hr)
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

    public List<JourneyResponse> getAllJourneys() {

        return journeyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JourneyResponse getByJourneyId(Long id) {
        Journey journey = journeyRepository.findById(id)
                .orElseThrow(()-> new AppException("Journey not found"));

        return mapToResponse(journey);
    }


    public JourneyResponse updateJourney(Long id, @Valid UpdateJourneyRequest request) {
        Journey journey = journeyRepository.findById(id)
                .orElseThrow(()-> new AppException("Journey not found"));

        journey.setTitle(request.getTitle());
        journey.setDescription(request.getDescription());

        journey =  journeyRepository.save(journey);
        return mapToResponse(journey);
    }

    public void deleteJourney(Long id) {
        Journey journey = journeyRepository.findById(id)
                .orElseThrow(()-> new AppException("Journey not found"));

        journeyRepository.delete(journey);
    }
}
