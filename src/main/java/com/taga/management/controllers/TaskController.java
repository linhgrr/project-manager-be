package com.taga.management.controllers;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api}")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    // add a new endpoint to get all tasks of user
    @GetMapping(value = "/tasks")
    public ResponseEntity<?> getAllTasks() {
        List<TaskResponseDTO> tasks;
        try {
            tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get tasks");
        }
    }

    // Add a new endpoint to get all tasks of a project
    @GetMapping(value = "/projects/{projectId}/tasks")
    public ResponseEntity<?> getTaskOfProject(@PathVariable Long projectId) {
        List<TaskResponseDTO> tasks;
        try {
            tasks = taskService.getTaskOfProject(projectId);
            return ResponseEntity.ok(tasks);
        }  catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get task");
        }
    }

    // Add a new endpoint to add or update a task
    @PostMapping(value = "/projects/{projectId}/tasks")
    public ResponseEntity<?> addOrUpdateTask(@RequestBody TaskInputDTO taskInputDTO, @PathVariable Long projectId) {
        try {
            taskInputDTO.setProjectId(projectId);
            return ResponseEntity.ok(taskService.addOrUpdateTask(taskInputDTO));
        }catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().body("You do not have permission to create tasks of this project");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add task");
        }
    }

    // Add a new endpoint to delete a task
    @DeleteMapping(value = "/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(taskService.deleteTask(projectId, taskId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete task");
        }
    }

    // Add a new endpoint to find tasks by title
    @GetMapping(value = "/projects/{projectId}/tasks/{title}")
    public List<TaskResponseDTO> findTasksByTitle(@PathVariable Long projectId, @PathVariable String title) {
        List<TaskResponseDTO> tasks;
        tasks = taskService.findTasksByTitle(title);
        return tasks;
    }
}
