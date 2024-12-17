package com.taga.management.converters;

import com.taga.management.DTOs.response.TaskCommentDTO;
import com.taga.management.models.Comment;
import com.taga.management.models.Task;
import com.taga.management.models.User;
import com.taga.management.repository.TaskRepository;
import com.taga.management.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CommentConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TaskRepository taskRepository;

    public TaskCommentDTO toTaskCommentDTO(Comment comment) {
        return modelMapper.map(comment, TaskCommentDTO.class);
    }

    public List<TaskCommentDTO> toTaskCommentDTOList(List<Comment> comments) {
        return comments.stream().map(this::toTaskCommentDTO).collect(Collectors.toList());
    }

    public Comment toComment(TaskCommentDTO taskCommentDTO) {
        Comment comment = modelMapper.map(taskCommentDTO, Comment.class);
        User user = SecurityUtils.getPrincipal();
        comment.setAuthor(user);
        Task task = taskRepository.getTaskById(taskCommentDTO.getTaskId());
        comment.setTask(task);
        return comment;
    }
}
