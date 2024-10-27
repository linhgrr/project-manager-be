package com.taga.management.converters;

import com.taga.management.DTOs.ManagerDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.DTOs.StaffDTO;
import com.taga.management.models.Project;
import com.taga.management.models.User;
import com.taga.management.models.response.ResponseProject;
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

    public List<ResponseProject> toResponseProjectList(List<Project> projectList) {
        List<ResponseProject> responseProjectList = new ArrayList<>();
        Long id = SecurityUtils.getPrincipal().getId();
        for(Project project : projectList) {
            ResponseProject responseProject = modelMapper.map(project, ResponseProject.class);
            setup(project, responseProject, id);
            responseProjectList.add(responseProject);
        }
        return responseProjectList;
    }

    public ResponseProject toResponseProject(Project project) {
        ResponseProject responseProject = modelMapper.map(project, ResponseProject.class);
        Long id = SecurityUtils.getPrincipal().getId();
        setup(project, responseProject, id);
        return responseProject;
    }

    private void setup(Project project, ResponseProject responseProject, Long id) {
        responseProject.setManager(false);
        for (User user: project.getManagers()){
            if(user.getId().equals(id)){
                responseProject.setManager(true);
                break;
            }
        }
        responseProject.setManagers(project.getManagers().stream().map(manager -> {
                    ManagerDTO managerDTO = new ManagerDTO();
                    managerDTO.setId(manager.getId());
                    managerDTO.setUsername(manager.getUsername());
                    managerDTO.setPictureUrl(manager.getPictureUrl());
                    return managerDTO;
                })
                .collect(Collectors.toList()));
        responseProject.setStaffs(project.getStaffs().stream().map(staff -> {
                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setUsername(staff.getUsername());
                    staffDTO.setPictureUrl(staff.getPictureUrl());
                    return staffDTO;
                })
                .collect(Collectors.toList()));
    }
}
