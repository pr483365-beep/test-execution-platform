package com.ramya.shop.projectservice.dto;
import com.ramya.shop.projectservice.entity.ProjectMemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProjectMemberResponse {

    private Long id;
    private Long projectId;
    private Long userId;
    private ProjectMemberRole role;
    private LocalDateTime joinedAt;

}
