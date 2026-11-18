package com.ramya.shop.projectservice.repository;

import com.ramya.shop.projectservice.entity.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestSuiteRepository extends JpaRepository<TestSuite,Long> {

    boolean existsByProjectId(Long projectId);

    List<TestSuite> findAllByProjectId(Long projectId);

    boolean existsByNameIgnoreCaseAndProjectId(String name, Long projectId);

    boolean existsByNameIgnoreCaseAndProjectIdAndIdNot(String name, Long projectId, Long id);
}
