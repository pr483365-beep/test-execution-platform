
package com.ramya.shop.projectservice.service;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.dto.TestSuiteRequest;
import com.ramya.shop.projectservice.entity.Project;
import com.ramya.shop.projectservice.entity.TestSuite;
import com.ramya.shop.projectservice.exception.DuplicateTestSuiteException;
import com.ramya.shop.projectservice.exception.ProjectNotFoundException;
import com.ramya.shop.projectservice.exception.SuiteHasTestCasesException;
import com.ramya.shop.projectservice.exception.SuiteNotFoundException;
import com.ramya.shop.projectservice.repository.ProjectRepository;
import com.ramya.shop.projectservice.repository.TestCaseRepository;
import com.ramya.shop.projectservice.repository.TestSuiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final ProjectRepository projectRepository;
    private final TestCaseRepository testCaseRepository;

    public TestSuiteService(TestSuiteRepository testSuiteRepository, ProjectRepository projectRepository,TestCaseRepository testCaseRepository) {

        this.testSuiteRepository = testSuiteRepository;
        this.projectRepository = projectRepository;
        this.testCaseRepository=testCaseRepository;
    }

    // Create Test Suite
    public ApiResponse<TestSuite> createTestSuite(TestSuiteRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with ID: " + request.getProjectId()
                ));

        if (testSuiteRepository.existsByNameIgnoreCaseAndProjectId(
                request.getName(), request.getProjectId())) {

            throw new DuplicateTestSuiteException(
                    "Test Suite already exists with name: " + request.getName()
            );
        }

        TestSuite testSuite = new TestSuite();

        testSuite.setName(request.getName());
        testSuite.setDescription(request.getDescription());
        testSuite.setProject(project);
        testSuite.setCreatedBy(request.getCreatedBy());
        testSuite.setCreatedByUname(request.getCreatedByUname());

        testSuiteRepository.save(testSuite);
        return new ApiResponse<>(HttpStatus.CREATED.value(),testSuite.getName()+" Created Successfully",testSuite);
    }

    // Get all suites belonging to a project
    public ApiResponse<List<TestSuite>> getSuitesByProjectId(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(
                    "Project not found with ID: " + projectId
            );
        }

       List<TestSuite> suites=testSuiteRepository.findAllByProjectId(projectId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Test suites retrieved successfully",suites);
    }


    public ApiResponse<TestSuite> updateTestSuite(Long suiteId, TestSuiteRequest request) {

        TestSuite existingSuite = testSuiteRepository.findById(suiteId)
                .orElseThrow(() -> new SuiteNotFoundException(
                        "Test suite not found with ID: " + suiteId
                ));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with ID: " + request.getProjectId()
                ));

        if (testSuiteRepository.existsByNameIgnoreCaseAndProjectIdAndIdNot(
                request.getName(), request.getProjectId(),suiteId)) {

            throw new DuplicateTestSuiteException(
                    "Test Suite already exists with name: " + request.getName()
            );
        }

        existingSuite.setName(request.getName());
        existingSuite.setDescription(request.getDescription());
        existingSuite.setProject(project);

        testSuiteRepository.save(existingSuite);
        return new ApiResponse<>(HttpStatus.OK.value(), request.getName()+" Updated Successfully",existingSuite);
    }


    public ApiResponse<Void> deleteTestSuite(Long suiteId) {

        TestSuite existingSuite = testSuiteRepository.findById(suiteId)
                .orElseThrow(() -> new SuiteNotFoundException(
                        "Test suite not found with ID: " + suiteId
                ));

        if (testCaseRepository.existsByTestSuiteId(suiteId)) {
            throw new SuiteHasTestCasesException(
                    "Cannot delete Test Suite because it contains Test Cases"
            );
        }

        testSuiteRepository.delete(existingSuite);
        return new ApiResponse<>(HttpStatus.OK.value(), existingSuite.getName()+" Deleted Successfully.",null);
    }


}
