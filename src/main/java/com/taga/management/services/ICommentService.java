package com.taga.management.services;

import com.taga.management.DTOs.response.TaskCommentDTO;

import java.util.List;

public interface ICommentService {
    List<TaskCommentDTO> getCommentsOfTask(Long taskId);
    TaskCommentDTO addCommentToTask(TaskCommentDTO taskCommentDTO);
}
