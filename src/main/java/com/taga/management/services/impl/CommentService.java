package com.taga.management.services.impl;

import com.taga.management.DTOs.response.TaskCommentDTO;
import com.taga.management.converters.CommentConverter;
import com.taga.management.models.Comment;
import com.taga.management.repository.CommentRepository;
import com.taga.management.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentConverter commentConverter;
    @Override
    public List<TaskCommentDTO> getCommentsOfTask(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return commentConverter.toTaskCommentDTOList(comments);
    }

    @Override
    public TaskCommentDTO addCommentToTask(TaskCommentDTO taskCommentDTO) {
        Comment comment = commentConverter.toComment(taskCommentDTO);
        commentRepository.save(comment);
        return commentConverter.toTaskCommentDTO(comment);
    }
}
