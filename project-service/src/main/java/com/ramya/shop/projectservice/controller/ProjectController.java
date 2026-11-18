
package com.ramya.shop.projectservice.controller;

import com.ramya.shop.projectservice.dto.*;
import com.ramya.shop.projectservice.service.ProjectMemberService;
import com.ramya.shop.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;

    public ProjectController(ProjectService projectService, ProjectMemberService projectMemberService) {
        this.projectService = projectService;
        this.projectMemberService=projectMemberService;
    }

    // Create project
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectRequest request) {
        ApiResponse<ProjectResponse> response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        ApiResponse<List<ProjectResponse>> response = projectService.getAllProjects();
        return ResponseEntity.ok(response);
    }

    // Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {
        ApiResponse<ProjectResponse> response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    // Delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        ApiResponse<Void> response = projectService.deleteProject(id);
        return ResponseEntity.ok(response);
    }

    // Update project
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        ApiResponse<ProjectResponse> response = projectService.updateProject(id, request);
        return ResponseEntity.ok(response);
    }

    //Update Status of Project
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateStatusOfProject(@PathVariable Long id, @Valid @RequestBody ProjectStatusRequest statusRequest) {
        ApiResponse<ProjectResponse> response = projectService.updateProjectStatus(id, statusRequest);
        return ResponseEntity.ok(response);
    }

    //Insert Project Member for Project
    @PostMapping("/{projectId}/members")
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> insertProjectMember(@Valid @RequestBody ProjectMemberRequest projectMemberRequest, @PathVariable Long projectId){
        ApiResponse<ProjectMemberResponse> response=projectMemberService.addMemberToProject(projectId,projectMemberRequest);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getAllMembersOfProject(@PathVariable Long projectId){
        ApiResponse<List<ProjectMemberResponse>> response=projectMemberService.getProjectMembers(projectId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteProjectMember(
            @PathVariable Long projectId,
            @PathVariable Long userId) {

        ApiResponse<Void> response = projectMemberService.deleteProjectMember(userId, projectId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{projectId}/members/{userId}/role")
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody ProjectMemberRoleRequest request) {

        ApiResponse<ProjectMemberResponse> response =
                projectMemberService.updateMemberRole(projectId, userId, request);

        return ResponseEntity.ok(response);
    }

}
