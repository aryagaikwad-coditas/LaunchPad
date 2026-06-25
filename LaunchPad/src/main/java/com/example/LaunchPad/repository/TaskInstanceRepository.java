package com.example.LaunchPad.repository;

import com.example.LaunchPad.entity.TaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskInstanceRepository extends JpaRepository<TaskInstance,Long> {
}
