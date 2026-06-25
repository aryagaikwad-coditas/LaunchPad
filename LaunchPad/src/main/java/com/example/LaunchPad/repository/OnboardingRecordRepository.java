package com.example.LaunchPad.repository;

import com.example.LaunchPad.entity.OnboardingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingRecordRepository extends JpaRepository<OnboardingRecord,Long> {
}
