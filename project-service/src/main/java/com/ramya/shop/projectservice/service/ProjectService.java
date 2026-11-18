
package com.ramya.shop.projectservice.service;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.dto.ProjectRequest;
import com.ramya.shop.projectservice.dto.ProjectResponse;
import com.ramya.shop.projectservice.dto.ProjectStatusRequest;
import com.ramya.shop.projectservice.entity.Project;
import com.ramya.shop.projectservice.exception.DuplicateProjectException;
import com.ramya.shop.projectservice.exception.ProjectHasResourcesException;
import com.ramya.shop.projectservice.exception.ProjectNotFoundException;
import com.ramya.shop.projectservice.repository.ProjectRepository;
import com.ramya.shop.projectservice.repository.TestSuiteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TestSuiteRepository testSuiteRepository;

    // Constructor injection
    public ProjectService(ProjectRepository projectRepository, TestSuiteRepository testSuiteRepository) {
        this.projectRepository = projectRepository;
        this.testSuiteRepository = testSuiteRepository;
    }

    // Create a project
    @Transactional
    public ApiResponse<ProjectResponse> createProject(ProjectRequest request) {

        String normalizedName = request.getName()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (projectRepository.existsByNormalizedName(normalizedName)) {
            throw new DuplicateProjectException(
                    "Project already exists with name: " + request.getName()
            );
        }

        Project newProject = new Project();

        newProject.setName(request.getName().trim());
        newProject.setDescription(request.getDescription());

        Project savedProject;

        try {
            savedProject = projectRepository.saveAndFlush(newProject);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateProjectException(
                    "Project already exists with name: " + request.getName()
            );
        }
        ProjectResponse projectResponse = convertToResponse(savedProject);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Project Created Successfully",
                projectResponse
        );
    }

    // Get all projects
    @Transactional(readOnly = true)
    public ApiResponse<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = projects.stream()
                .map(this::convertToResponse)
                .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), "Projects Found", projectResponses);
    }

    // Get project by ID
    @Transactional(readOnly = true)
    public ApiResponse<ProjectResponse> getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with ID: " + id));
        ProjectResponse projectResponse = convertToResponse(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project Found with Id :" + id, projectResponse);
    }

    // Delete a project
    @Transactional
    public ApiResponse<Void> deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with ID: " + projectId
                        ));

        if (testSuiteRepository.existsByProjectId(projectId)) {
            throw new ProjectHasResourcesException(
                    "Cannot delete Project because it contains Test Suites"
            );
        }
        projectRepository.delete(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project deleted with Id : " + projectId, null);
    }

    // Update a project
    @Transactional
    public ApiResponse<ProjectResponse> updateProject(Long projectId, ProjectRequest request) {

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with ID: " + projectId
                ));

        String normalizedName = request.getName()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (projectRepository.existsByNormalizedNameAndIdNot(normalizedName, projectId)) {
            throw new DuplicateProjectException(
                    "Project already exists with name: " + request.getName()
            );
        }

        existingProject.setName(request.getName().trim());
        existingProject.setDescription(request.getDescription());

        Project savedProject;

        try {
            savedProject = projectRepository.saveAndFlush(existingProject);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateProjectException(
                    "Project already exists with name: " + request.getName()
            );
        }

        ProjectResponse projectResponse = convertToResponse(savedProject);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Project Updated Successfully",
                projectResponse
        );
    }

    private ProjectResponse convertToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setStatus(project.getStatus());
        response.setCreatedAt(project.getCreatedAt());
        response.setCreatedBy(project.getCreatedBy());
        response.setCreatedByUname(project.getCreatedByUname());

        return response;
    }

    @Transactional
    public ApiResponse<ProjectResponse> updateProjectStatus(Long projectId, ProjectStatusRequest request) {

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with ID: " + projectId
                ));

        existingProject.setStatus(request.getStatus());

        Project project =projectRepository.save(existingProject);
        ProjectResponse projectResponse=convertToResponse(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project Status updated Successfully .",projectResponse);
    }
}
