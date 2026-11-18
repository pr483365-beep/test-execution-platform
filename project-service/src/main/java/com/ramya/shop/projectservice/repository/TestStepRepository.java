package com.ramya.shop.projectservice.repository;

import com.ramya.shop.projectservice.entity.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestStepRepository extends JpaRepository<TestStep, Long> {

    List<TestStep> findAllByTestCaseId(Long testCaseId);

    void deleteAllByTestCaseId(Long testCaseId);
}