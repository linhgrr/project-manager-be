package com.taga.management.services;

import com.taga.management.DTOs.AssignStaffDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.DTOs.response.ProjectResponseDTO;

import java.util.List;

public interface IProjectService {
    List<ProjectResponseDTO> getAllProjects();
    void createProject(ProjectDTO projectDTO) throws Exception;
    void deleteProject(Long projectId) throws Exception;
    ProjectResponseDTO getProjectById(Long projectId);
    void assignStaff(AssignStaffDTO assignStaffDTO) throws Exception;
}
