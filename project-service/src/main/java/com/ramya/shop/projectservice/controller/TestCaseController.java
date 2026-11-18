
package com.ramya.shop.projectservice.controller;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.dto.TestCaseRequest;
import com.ramya.shop.projectservice.dto.TestCaseResponse;
import com.ramya.shop.projectservice.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-cases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    // Create Test Case
    @PostMapping
    public ResponseEntity<ApiResponse<TestCaseResponse>> createTestCase(
            @RequestBody @Valid TestCaseRequest request) {

        ApiResponse<TestCaseResponse> response = testCaseService.createTestCase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get Test Case by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestCaseResponse>> getTestCaseById(@PathVariable Long id) {

        ApiResponse<TestCaseResponse> response = testCaseService.getTestCaseById(id);

        return ResponseEntity.ok(response);
    }

    // Get all Test Cases by Test Suite ID
    @GetMapping("/suite/{suiteId}")
    public ResponseEntity<ApiResponse<List<TestCaseResponse>>> getAllTestCasesBySuiteId(@PathVariable Long suiteId) {

        ApiResponse<List<TestCaseResponse>> response = testCaseService.getAllTestCasesBySuiteId(suiteId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TestCaseResponse>> updateTestCase(
            @PathVariable Long id,
            @RequestBody @Valid TestCaseRequest request) {

        ApiResponse<TestCaseResponse> response = testCaseService.updateTestCase(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTestCase(@PathVariable Long id) {

        ApiResponse<Void> voidApiResponse=testCaseService.deleteTestCase(id);

        return ResponseEntity.ok(voidApiResponse);
    }


}
