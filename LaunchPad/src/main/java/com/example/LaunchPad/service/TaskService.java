package com.example.LaunchPad.service;

import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.repository.JourneyRepository;
import com.example.LaunchPad.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    private final JourneyRepository journeyRepository;
}
