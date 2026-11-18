package com.ramya.shop.projectservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestStepRequest {

    @NotNull(message = "Step number is required")
    @Min(value = 1, message = "Step number must be at least 1")
    private Integer stepNumber;

    @NotBlank(message = "Action is required")
    private String action;

    private String input;

    @NotBlank(message = "Expected result is required")
    private String expectedResult;
}