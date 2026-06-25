package com.example.LaunchPad.repository;

import com.example.LaunchPad.entity.Journey;
import com.example.LaunchPad.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByJourneyId(Long journeyId);
}
