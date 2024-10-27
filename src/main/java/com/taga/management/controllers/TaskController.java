package com.taga.management.controllers;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.ResponseEntity;
import com.taga.management.models.Task;
import com.taga.management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping("${api}/projects")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{projectId}/tasks")
    public ArrayList<TaskResponseDTO> getTaskOfProject(@PathVariable Long projectId) {
        ArrayList<TaskResponseDTO> tasks = new ArrayList<>();
        tasks = taskService.getTaskOfProject(projectId);
        return tasks;
    }

    @PostMapping(value = "/{projectId}/tasks")
    public ResponseEntity addOrUpdateTask(@RequestBody TaskInputDTO taskInputDTO, @PathVariable Long projectId) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            taskInputDTO.setProjectId(projectId);
            responseEntity = taskService.addOrUpdateTask(taskInputDTO);
        } catch (Exception e) {
            responseEntity.setMessage("Failed to add task");
            return responseEntity;
        }
        return responseEntity;
    }


}
