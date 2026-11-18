package com.ramya.shop.projectservice.service;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.dto.ProjectMemberRequest;
import com.ramya.shop.projectservice.dto.ProjectMemberResponse;
import com.ramya.shop.projectservice.dto.ProjectMemberRoleRequest;
import com.ramya.shop.projectservice.entity.Project;
import com.ramya.shop.projectservice.entity.ProjectMember;
import com.ramya.shop.projectservice.exception.DuplicateProjectMemberException;
import com.ramya.shop.projectservice.exception.ProjectMemberNotFoundException;
import com.ramya.shop.projectservice.exception.ProjectNotFoundException;
import com.ramya.shop.projectservice.repository.ProjectMemberRepository;
import com.ramya.shop.projectservice.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    //Constructor Injection
    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,ProjectRepository projectRepository){
        this.projectMemberRepository=projectMemberRepository;
        this.projectRepository=projectRepository;
    }

    @Transactional
    public ApiResponse<ProjectMemberResponse> addMemberToProject(
            Long projectId,
            ProjectMemberRequest request) {

        Long userId=request.getUserId();

        Project existingProject = projectRepository.findById(projectId).orElseThrow(()->
                 new ProjectNotFoundException("Project Not found with the Id :"+projectId));

        if(projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)){
            throw new DuplicateProjectMemberException("Project Member with Id :"+userId +" already exists in Project");
        }

        ProjectMember projectMember= new ProjectMember();
        projectMember.setUserId(userId);
        projectMember.setRole(request.getRole());
        projectMember.setProject(existingProject);

        ProjectMember savedProjectMember= projectMemberRepository.save(projectMember);
        ProjectMemberResponse projectMemberResponse=convertResponse(savedProjectMember);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Project Member added successfully",projectMemberResponse);
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProjectMemberResponse>> getProjectMembers(Long projectId) {
        projectRepository.findById(projectId).orElseThrow(()->
                new ProjectNotFoundException("Project Not found with the Id :"+projectId));

        List<ProjectMember> projectMembers=projectMemberRepository.findAllByProjectId(projectId);
        List<ProjectMemberResponse> projectMemberResponses= projectMembers.stream().map(this::convertResponse).toList();
        return new ApiResponse<>(HttpStatus.OK.value(),"Fetched All Members from Project Successfully",projectMemberResponses);
    }

    @Transactional
    public ApiResponse<Void> deleteProjectMember(Long userId,Long projectId){
        // Check whether the project exists
        projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with ID: " + projectId
                        ));

        if(!projectMemberRepository.existsByProjectIdAndUserId(projectId,userId)){
            throw new ProjectMemberNotFoundException("Project Member was not found with userId : "+userId);
        }
        projectMemberRepository.deleteByProjectIdAndUserId(projectId,userId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project Member deleted Successfully",null);
    }

    private ProjectMemberResponse convertResponse(ProjectMember projectMember){
      return  ProjectMemberResponse.builder().userId(projectMember.getUserId()).role(projectMember.getRole()).projectId(projectMember.getProject().getId()).id(projectMember.getId()).joinedAt(projectMember.getJoinedAt()).build();
    }


    @Transactional
    public ApiResponse<ProjectMemberResponse> updateMemberRole(Long projectId, Long userId, ProjectMemberRoleRequest request) {

        ProjectMember projectMember=projectMemberRepository.findByProjectIdAndUserId(projectId,userId).orElseThrow(()->
                new ProjectMemberNotFoundException("Project Member not found with User Id : "+userId));

        projectMember.setRole(request.getRole());
        ProjectMember savedProjectMember=projectMemberRepository.save(projectMember);
        ProjectMemberResponse projectMemberResponse=convertResponse(savedProjectMember);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project Member Role Updated Successfully",projectMemberResponse);
    }


}

