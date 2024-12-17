package com.taga.management.controllers;

import com.taga.management.DTOs.response.SubTaskDTO;
import com.taga.management.services.ISubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/sub-tasks")
public class SubTaskController {
    @Autowired
    private ISubTaskService subTaskService;

    @GetMapping
    public ResponseEntity<?> getSubTasksOfTask(@PathVariable Long taskId) {
        try {
            List<SubTaskDTO> subTasks = subTaskService.getSubTasksOfTask(taskId);
            return ResponseEntity.ok(subTasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get sub-tasks");
        }
    }

    @PostMapping
    public ResponseEntity<?> addOrUpdateSubTask(@RequestBody SubTaskDTO subTaskDTO) {
        try {
            SubTaskDTO subTask = subTaskService.addOrUpdateSubTask(subTaskDTO);
            return ResponseEntity.ok(subTask);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add or update sub-task");
        }
    }
}
