package com.taga.management.controllers;

import com.taga.management.DTOs.response.TaskCommentDTO;
import com.taga.management.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping
    public ResponseEntity<?> getCommentsOfTask(@PathVariable Long taskId) {
        try {
            List<TaskCommentDTO> comments = commentService.getCommentsOfTask(taskId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get comments");
        }
    }
    @PostMapping
    public ResponseEntity<?> addCommentToTask(@RequestBody TaskCommentDTO taskCommentDTO) {
        try {
            TaskCommentDTO comment = commentService.addCommentToTask(taskCommentDTO);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add comment");
        }
    }

}
