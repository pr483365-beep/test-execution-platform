
package com.ramya.shop.projectservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class TestCaseResponse {

    private Long id;
    private String title;
    private String description;
    private String preconditions;
    private String priority;
    private Long testSuiteId;
    private List<TestStepResponse> steps;
}
