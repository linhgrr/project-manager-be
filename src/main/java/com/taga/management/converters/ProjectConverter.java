package com.taga.management.converters;

import com.taga.management.DTOs.ManagerDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.DTOs.StaffDTO;
import com.taga.management.models.Project;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.ProjectResponseDTO;
import com.taga.management.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ProjectConverter {
    @Autowired
    private ModelMapper modelMapper;

    public Project toProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    public List<ProjectResponseDTO> toResponseProjectList(List<Project> projectList) {
        List<ProjectResponseDTO> projectResponseDTOList = new ArrayList<>();
        Long id = SecurityUtils.getPrincipal().getId();
        for(Project project : projectList) {
            ProjectResponseDTO projectResponseDTO = modelMapper.map(project, ProjectResponseDTO.class);
            setup(project, projectResponseDTO, id);
            projectResponseDTOList.add(projectResponseDTO);
        }
        return projectResponseDTOList;
    }

    public ProjectResponseDTO toResponseProject(Project project) {
        ProjectResponseDTO projectResponseDTO = modelMapper.map(project, ProjectResponseDTO.class);
        Long id = SecurityUtils.getPrincipal().getId();
        setup(project, projectResponseDTO, id);
        return projectResponseDTO;
    }

    private void setup(Project project, ProjectResponseDTO projectResponseDTO, Long id) {
        projectResponseDTO.setManager(false);
        for (User user: project.getManagers()){
            if(user.getId().equals(id)){
                projectResponseDTO.setManager(true);
                break;
            }
        }
        projectResponseDTO.setManagers(project.getManagers().stream().map(manager -> {
                    ManagerDTO managerDTO = new ManagerDTO();
                    managerDTO.setId(manager.getId());
                    managerDTO.setUsername(manager.getUsername());
                    managerDTO.setPictureUrl(manager.getPictureUrl());
                    return managerDTO;
                })
                .collect(Collectors.toList()));
        projectResponseDTO.setStaffs(project.getStaffs().stream().map(staff -> {
                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setUsername(staff.getUsername());
                    staffDTO.setPictureUrl(staff.getPictureUrl());
                    return staffDTO;
                })
                .collect(Collectors.toList()));
    }
}
