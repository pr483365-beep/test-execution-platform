package com.ramya.shop.projectservice.dto;

import com.ramya.shop.projectservice.entity.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatusRequest {

    @NotNull(message = "Project status is required")
    private ProjectStatus status;
}
