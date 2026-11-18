package com.ramya.shop.projectservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TestSuiteRequest {

    @NotBlank(message = "Test suite name is required")
    @Size(max = 100, message = "Test suite name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Project ID is required")
    @Positive(message = "Project ID must be greater than zero")
    private Long projectId;

    private String createdBy;

    private String createdByUname;
}