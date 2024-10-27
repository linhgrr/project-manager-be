package com.taga.management.controllers;

import com.taga.management.DTOs.AssignStaffDTO;
import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.exceptions.ProjectNotFoundException;
import com.taga.management.models.ResponseEntity;
import com.taga.management.services.impl.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api}/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity findAllProjects() {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            responseEntity.setData(projectService.getAllProjects());
            responseEntity.setMessage("Successfully retrieved all projects");
        }  catch (Exception e) {
            responseEntity.setMessage(e.getMessage());
        }

        return responseEntity;
    }
    @GetMapping("/{id}")
    public ResponseEntity findProjectById(@PathVariable("id") Long id) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            responseEntity.setData(projectService.getProjectById(id));
            responseEntity.setMessage("Successfully retrieved project by id");
        } catch (EntityNotFoundException e) {
            responseEntity.setMessage(e.getMessage());
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity createOrUpdateProject(@RequestBody @Valid ProjectDTO projectDTO) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            projectService.createProject(projectDTO);
            responseEntity.setMessage("Successfully created project");

        } catch (Exception e) {
            responseEntity.setMessage(e.getMessage());
        }
        return responseEntity;
    }

    @PostMapping("/assign")
    public ResponseEntity assignStaff(@RequestBody AssignStaffDTO assignStaffDTO) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            projectService.assignStaff(assignStaffDTO);
            responseEntity.setMessage("Assign staff success");
        } catch (ProjectNotFoundException e) {
            responseEntity.setMessage("Project not found");
        } catch (AccessDeniedException e) {
            responseEntity.setMessage("You do not have permission to assign staff to this project");
        } catch (Exception e) {
            // Log exception for further analysis
            e.printStackTrace();
            responseEntity.setMessage("An unexpected error occurred");
        }
        return responseEntity;
    }

}
