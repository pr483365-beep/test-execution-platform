
package com.ramya.shop.projectservice.controller;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.dto.TestSuiteRequest;
import com.ramya.shop.projectservice.entity.TestSuite;
import com.ramya.shop.projectservice.service.TestSuiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-suites")
public class TestSuiteController {

    private final TestSuiteService testSuiteService;

    public TestSuiteController(TestSuiteService testSuiteService) {
        this.testSuiteService = testSuiteService;
    }

    // Create Test Suite
    @PostMapping
    public ResponseEntity<ApiResponse<TestSuite>> createTestSuite(@RequestBody @Valid TestSuiteRequest request) {

        ApiResponse<TestSuite> testSuite = testSuiteService.createTestSuite(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(testSuite);
    }

    // Get suites by project ID
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<TestSuite>>> getSuitesByProjectId(@PathVariable Long projectId) {

        ApiResponse<List<TestSuite>> suites = testSuiteService.getSuitesByProjectId(projectId);

        return ResponseEntity.ok(suites);
    }


    @PutMapping("/{suiteId}")
    public ResponseEntity<ApiResponse<TestSuite>> updateTestSuite(
            @PathVariable Long suiteId,
            @RequestBody TestSuiteRequest request) {

        ApiResponse<TestSuite> updatedSuite =
                testSuiteService.updateTestSuite(suiteId, request);

        return ResponseEntity.ok(updatedSuite);
    }


    @DeleteMapping("/{suiteId}")
    public ResponseEntity<ApiResponse<Void>> deleteTestSuite(
            @PathVariable Long suiteId) {

        ApiResponse<Void> response=testSuiteService.deleteTestSuite(suiteId);
        return ResponseEntity.ok(response);
    }


}
