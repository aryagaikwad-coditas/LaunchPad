package com.example.LaunchPad.repository;

import com.example.LaunchPad.constants.TaskStatus;
import com.example.LaunchPad.entity.TaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskInstanceRepository extends JpaRepository<TaskInstance,Long> {
    List<TaskInstance> findByOnboardingRecordId(Long onboardingRecordId);
    long countByOnboardingRecordIdAndTaskStatusNot(Long onboardingRecordId, TaskStatus taskStatus);
}
