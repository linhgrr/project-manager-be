package com.taga.management.services;

import com.taga.management.DTOs.AssignStaffDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.models.Project;
import com.taga.management.models.response.ResponseProject;

import java.util.List;

public interface IProjectService {
    List<ResponseProject> getAllProjects();
    void createProject(ProjectDTO projectDTO) throws Exception;
    ResponseProject getProjectById(Long projectId);
    void assignStaff(AssignStaffDTO assignStaffDTO) throws Exception;
}
