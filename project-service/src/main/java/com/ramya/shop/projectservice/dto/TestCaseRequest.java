package com.ramya.shop.projectservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TestCaseRequest {

    @NotBlank(message = "Test case title is required")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String preconditions;

    @NotBlank(message = "Priority is required")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH",
            message = "Priority must be LOW, MEDIUM or HIGH"
    )
    private String priority;

    @NotNull(message = "Test suite ID is required")
    @Positive(message = "Test suite ID must be greater than zero")
    private Long testSuiteId;

    @NotEmpty(message = "At least one test step is required")
    @Valid
    private List<TestStepRequest> steps;
}