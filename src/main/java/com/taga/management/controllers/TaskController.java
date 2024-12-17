package com.taga.management.controllers;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.models.ResponseEntity;
import com.taga.management.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api}")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    // add a new endpoint to get all tasks of user
    @GetMapping(value = "/tasks")
    public ResponseEntity getAllTasks() {
        ResponseEntity responseEntity = new ResponseEntity();
        List<TaskResponseDTO> tasks;
        try {
            tasks = taskService.getAllTasks();
            responseEntity.setMessage("Get task success");
            responseEntity.setData(tasks);
        } catch (Exception e) {
            responseEntity.setMessage("Failed to get task");
            return responseEntity;
        }
        return responseEntity;
    }

    // Add a new endpoint to get all tasks of a project
    @GetMapping(value = "/projects/{projectId}/tasks")
    public ResponseEntity getTaskOfProject(@PathVariable Long projectId) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<TaskResponseDTO> tasks;
        try {
            tasks = taskService.getTaskOfProject(projectId);
            responseEntity.setMessage("Get task success");
            responseEntity.setData(tasks);
        }  catch (AccessDeniedException e) {
            responseEntity.setMessage(e.getMessage());
        }
        catch (Exception e) {
            responseEntity.setMessage("Failed to get task");
            return responseEntity;
        }
        return responseEntity;
    }

    // Add a new endpoint to add or update a task
    @PostMapping(value = "/projects/{projectId}/tasks")
    public ResponseEntity addOrUpdateTask(@RequestBody TaskInputDTO taskInputDTO, @PathVariable Long projectId) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
                taskInputDTO.setProjectId(projectId);
            responseEntity = taskService.addOrUpdateTask(taskInputDTO);
        }catch (AccessDeniedException e) {
            responseEntity.setMessage("You do not have permission to create tasks of this project");
        }
        catch (Exception e) {
            responseEntity.setMessage("Failed to add task");
            return responseEntity;
        }
        return responseEntity;
    }

    // Add a new endpoint to delete a task
    @DeleteMapping(value = "/projects/{projectId}/tasks/{taskId}")
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
    @GetMapping(value = "/projects/{projectId}/tasks/{title}")
    public List<TaskResponseDTO> findTasksByTitle(@PathVariable Long projectId, @PathVariable String title) {
        List<TaskResponseDTO> tasks;
        tasks = taskService.findTasksByTitle(title);
        return tasks;
    }

    
}
