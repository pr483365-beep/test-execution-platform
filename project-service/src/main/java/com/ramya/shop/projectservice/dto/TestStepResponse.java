
package com.ramya.shop.projectservice.dto;

import lombok.Data;

@Data
public class TestStepResponse {

    private Long id;
    private Integer stepNumber;
    private String action;
    private String input;
    private String expectedResult;
}
