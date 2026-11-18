package com.ramya.shop.projectservice.dto;

import com.ramya.shop.projectservice.entity.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
    private String createdByUname;
}