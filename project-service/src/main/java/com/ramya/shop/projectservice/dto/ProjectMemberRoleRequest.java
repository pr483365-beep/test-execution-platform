package com.ramya.shop.projectservice.dto;

import com.ramya.shop.projectservice.entity.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberRoleRequest {

    @NotNull(message="Project member role is required")
    private ProjectMemberRole role;
}
