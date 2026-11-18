package com.ramya.shop.projectservice.repository;

import com.ramya.shop.projectservice.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findAllByTestSuiteId(Long testSuiteId);

    boolean existsByTestSuiteId(Long testSuiteId);
}