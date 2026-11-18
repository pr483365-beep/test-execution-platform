
package com.ramya.shop.projectservice.service;

import com.ramya.shop.projectservice.dto.*;
import com.ramya.shop.projectservice.entity.TestCase;
import com.ramya.shop.projectservice.entity.TestStep;
import com.ramya.shop.projectservice.entity.TestSuite;
import com.ramya.shop.projectservice.exception.SuiteNotFoundException;
import com.ramya.shop.projectservice.exception.TestCaseNotFoundException;
import com.ramya.shop.projectservice.repository.TestCaseRepository;
import com.ramya.shop.projectservice.repository.TestStepRepository;
import com.ramya.shop.projectservice.repository.TestSuiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final TestStepRepository testStepRepository;
    private final TestSuiteRepository testSuiteRepository;

    public TestCaseService(
            TestCaseRepository testCaseRepository,
            TestStepRepository testStepRepository,
            TestSuiteRepository testSuiteRepository) {
        this.testCaseRepository = testCaseRepository;
        this.testStepRepository = testStepRepository;
        this.testSuiteRepository = testSuiteRepository;
    }

    @Transactional
    public ApiResponse<TestCaseResponse> createTestCase(TestCaseRequest request) {

        TestSuite testSuite = testSuiteRepository
                .findById(request.getTestSuiteId())
                .orElseThrow(() ->
                        new SuiteNotFoundException("Test Suite not found with ID: " + request.getTestSuiteId()));

        TestCase testCase = new TestCase();
        testCase.setTitle(request.getTitle());
        testCase.setDescription(request.getDescription());
        testCase.setPreconditions(request.getPreconditions());
        testCase.setPriority(request.getPriority());
        testCase.setTestSuite(testSuite);

        TestCase savedTestCase = testCaseRepository.save(testCase);

        List<TestStep> steps = new ArrayList<>();

        for (TestStepRequest stepRequest : request.getSteps()) {

            TestStep step = new TestStep();

            step.setStepNumber(stepRequest.getStepNumber());
            step.setAction(stepRequest.getAction());
            step.setInput(stepRequest.getInput());
            step.setExpectedResult(stepRequest.getExpectedResult());
            step.setTestCase(savedTestCase);

            steps.add(step);
        }

        testStepRepository.saveAll(steps);

        TestCaseResponse testCaseResponse = convertToResponse(savedTestCase);

        return new ApiResponse<>(HttpStatus.CREATED.value(), request.getTitle() + " created successfully.", testCaseResponse);
    }

    public ApiResponse<TestCaseResponse> getTestCaseById(Long id) {

        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() ->
                        new TestCaseNotFoundException("Test Case not found with ID: " + id));

        TestCaseResponse testCaseResponse= convertToResponse(testCase);
        return new ApiResponse<>(HttpStatus.OK.value(), "TestCase fetched successfully",testCaseResponse);
    }

    public ApiResponse<List<TestCaseResponse>> getAllTestCasesBySuiteId(Long suiteId) {

        if (!testSuiteRepository.existsById(suiteId)) {
            throw new SuiteNotFoundException("Test Suite not found with ID: " + suiteId);
        }

        List<TestCase> testCases = testCaseRepository.findAllByTestSuiteId(suiteId);

        List<TestCaseResponse> testCaseResponseList= testCases.stream()
                .map(this::convertToResponse)
                .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), "TestCases Fetched successfully",testCaseResponseList);
    }


    @Transactional
    public ApiResponse<TestCaseResponse> updateTestCase(Long id, TestCaseRequest request) {

        TestCase existingTestCase = testCaseRepository.findById(id)
                .orElseThrow(() ->
                        new TestCaseNotFoundException(
                                "Test Case not found with ID: " + id));

        TestSuite testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                .orElseThrow(() ->
                        new SuiteNotFoundException(
                                "Test Suite not found with ID: " + request.getTestSuiteId()));

        existingTestCase.setTitle(request.getTitle());
        existingTestCase.setDescription(request.getDescription());
        existingTestCase.setPreconditions(request.getPreconditions());
        existingTestCase.setPriority(request.getPriority());
        existingTestCase.setTestSuite(testSuite);

        TestCase savedTestCase = testCaseRepository.save(existingTestCase);

        testStepRepository.deleteAllByTestCaseId(id);

        List<TestStep> updatedSteps = new ArrayList<>();

        for (TestStepRequest stepRequest : request.getSteps()) {

            TestStep step = new TestStep();

            step.setStepNumber(stepRequest.getStepNumber());
            step.setAction(stepRequest.getAction());
            step.setInput(stepRequest.getInput());
            step.setExpectedResult(stepRequest.getExpectedResult());
            step.setTestCase(savedTestCase);

            updatedSteps.add(step);
        }

        testStepRepository.saveAll(updatedSteps);

        TestCaseResponse testCaseResponse= convertToResponse(savedTestCase);
        return new ApiResponse<>(HttpStatus.OK.value(),request.getTitle()+" updated Successfully.",testCaseResponse);
    }

    @Transactional
    public ApiResponse<Void> deleteTestCase(Long id) {

        TestCase existingTestCase = testCaseRepository.findById(id)
                .orElseThrow(() ->
                        new TestCaseNotFoundException(
                                "Test Case not found with ID: " + id));

        testStepRepository.deleteAllByTestCaseId(id);

        testCaseRepository.delete(existingTestCase);

        return new ApiResponse<>(HttpStatus.OK.value(), existingTestCase.getTitle()+" deleted Successfully.",null);
    }

    private TestCaseResponse convertToResponse(TestCase testCase) {

        TestCaseResponse response = new TestCaseResponse();

        response.setId(testCase.getId());
        response.setTitle(testCase.getTitle());
        response.setDescription(testCase.getDescription());
        response.setPreconditions(testCase.getPreconditions());
        response.setPriority(testCase.getPriority());
        response.setTestSuiteId(testCase.getTestSuite().getId());

        List<TestStep> steps = testStepRepository.findAllByTestCaseId(testCase.getId());

        List<TestStepResponse> stepResponses = steps.stream()
                .map(step -> {
                    TestStepResponse stepResponse = new TestStepResponse();

                    stepResponse.setId(step.getId());
                    stepResponse.setStepNumber(step.getStepNumber());
                    stepResponse.setAction(step.getAction());
                    stepResponse.setInput(step.getInput());
                    stepResponse.setExpectedResult(step.getExpectedResult());

                    return stepResponse;
                })
                .toList();

        response.setSteps(stepResponses);

        return response;
    }

}
