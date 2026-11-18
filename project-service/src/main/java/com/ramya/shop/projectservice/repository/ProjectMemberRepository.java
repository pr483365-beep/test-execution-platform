package com.ramya.shop.projectservice.repository;

import com.ramya.shop.projectservice.entity.Project;
import com.ramya.shop.projectservice.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {

    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

    List<ProjectMember> findAllByProjectId(Long projectId);

    void deleteByProjectIdAndUserId(Long projectId,Long userId);

    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);
}
