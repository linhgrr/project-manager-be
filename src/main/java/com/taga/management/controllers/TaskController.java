package com.taga.management.controllers;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.models.Task;
import com.taga.management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController(value = "/projects")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{projectId}/tasks")
    public ArrayList<Task> getTaskOfProject(@PathVariable Long projectId) {
        return taskService.getTaskOfProject(projectId);

    }

    @PostMapping
    public void addOrUpdateTask(@RequestBody TaskInputDTO taskInputDTO) {
        taskService.addOrUpdateTask(taskInputDTO);
        System.out.println("Add task notification");
    }
}
