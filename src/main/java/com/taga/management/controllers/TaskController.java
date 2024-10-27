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

    // Add a new endpoint to get all tasks of a project
    @GetMapping(value = "/{projectId}/tasks")
    public ArrayList<TaskResponseDTO> getTaskOfProject(@PathVariable Long projectId) {
        ArrayList<TaskResponseDTO> tasks = new ArrayList<>();
        tasks = taskService.getTaskOfProject(projectId);
        return tasks;
    }

    // Add a new endpoint to add or update a task
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

    // Add a new endpoint to delete a task
    @DeleteMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            responseEntity = taskService.deleteTask(projectId, taskId);
        } catch (Exception e) {
            responseEntity.setMessage("Failed to delete task");
            return responseEntity;
        }
        return responseEntity;
    }

    // Add a new endpoint to find tasks by title
    @GetMapping(value = "/{projectId}/tasks/{title}")
    public ArrayList<TaskResponseDTO> findTasksByTitle(@PathVariable Long projectId, @PathVariable String title) {
        ArrayList<TaskResponseDTO> tasks = new ArrayList<>();
        tasks = taskService.findTasksByTitle(title);
        return tasks;
    }
}
