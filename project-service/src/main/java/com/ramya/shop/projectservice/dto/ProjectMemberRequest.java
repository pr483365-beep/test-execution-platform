package com.ramya.shop.projectservice.dto;

import com.ramya.shop.projectservice.entity.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than zero")
    private Long userId;
    @NotNull(message = "Project member role is required")
    private ProjectMemberRole role;

}
