package com.example.LaunchPad.repository;

import com.example.LaunchPad.entity.OnboardingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OnboardingRecordRepository extends JpaRepository<OnboardingRecord,Long> {
    Optional<OnboardingRecord> findByNewHireId(Long newHireId);
    List<OnboardingRecord> findByManagerId(Long managerId);
    boolean existsByNewHireId(Long newHireId);
}
