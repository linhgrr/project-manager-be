package com.taga.management.services.impl;

import com.taga.management.DTOs.AssignStaffDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.converters.ProjectConverter;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.exceptions.ProjectNotFoundException;
import com.taga.management.models.Project;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.ProjectResponseDTO;
import com.taga.management.repository.ProjectRepository;
import com.taga.management.services.IProjectService;
import com.taga.management.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements IProjectService{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectConverter projectConverter;

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        Long id = SecurityUtils.getPrincipal().getId();
        User user = userService.findById(id);
        return projectConverter.toResponseProjectList(user.getJoinedProjects());
    }

    @Override
    public void createProject(ProjectDTO projectDTO) {
        Project project = projectConverter.toProject(projectDTO);
        Long id = SecurityUtils.getPrincipal().getId();
        User user = userService.findById(id);

        // check so luong project cua user
        if (user.getManagedProjects().size() >= 2) {
            throw new AccessDeniedException("User can only join up to 2 projects");
        }
        //Set owner la prj manager va staffs
        List<User> staffs = new ArrayList<>();
        staffs.add(user);
        project.setStaffs(staffs);

        List<User> managers = new ArrayList<>();
        managers.add(user);
        project.setManagers(managers);

        try {
            projectRepository.save(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {
        ProjectResponseDTO projectResponseDTO;
        Project project = projectRepository.findById(projectId).orElse(null);
        projectResponseDTO = projectConverter.toResponseProject(project);
        return projectResponseDTO;
    }

    @Override
    public void assignStaff(AssignStaffDTO assignStaffDTO) {
        Project project = projectRepository.findById(assignStaffDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        Long userId = SecurityUtils.getPrincipal().getId();
        boolean isManager = project.getManagers().stream().anyMatch(user -> user.getId().equals(userId));

        if (!isManager) {
            throw new AccessDeniedException("User does not have permission to assign staff to this project");
        }

        projectRepository.assignStaff(assignStaffDTO);
    }
}
