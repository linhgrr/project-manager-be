package com.taga.management.controllers;

import com.taga.management.DTOs.ProjectDTO;
import com.taga.management.models.ResponseEntity;
import com.taga.management.services.impl.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{projectId}/assign")
    public ResponseEntity assignStaff(@PathVariable Long projectId, @RequestBody Long staffId){
        ResponseEntity responseEntity = new ResponseEntity();
        return responseEntity;
    }
}
