package com.taga.management.converters;

import com.taga.management.DTOs.AssigneeDTO;
import com.taga.management.DTOs.ManagerDTO;
import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskCommentDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.DTOs.response.UserResponseDTO;
import com.taga.management.models.Comment;
import com.taga.management.models.Project;
import com.taga.management.models.Task;
import com.taga.management.models.User;
import com.taga.management.repository.ProjectRepository;
import com.taga.management.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskConverter {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Task convertToTask(TaskInputDTO taskInputDTO) {
        Task task = modelMapper.map(taskInputDTO, Task.class);
        Project project = projectRepository.findById(taskInputDTO.getProjectId())
                .orElseThrow(EntityNotFoundException::new);
        task.setProject(project);
        task.setCreator(SecurityUtils.getPrincipal());
        User assignee = project.getStaffs().stream()
                .filter(user -> user.getId().equals(taskInputDTO.getAssigneeId()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
        task.setAssignee(assignee);

        return task;
    }

    public List<TaskResponseDTO> toTaskResponseDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(this::convertToTaskResponseDTO)
                .toList();
    }

    private TaskResponseDTO convertToTaskResponseDTO(Task task) {
        TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
        if (task.getAssignee() != null) {
            taskResponseDTO.setAssignee(modelMapper.map(task.getAssignee(), AssigneeDTO.class));
        }
        if (task.getCreator() != null) {
            taskResponseDTO.setCreator(modelMapper.map(task.getCreator(), ManagerDTO.class));
        }
        taskResponseDTO.setComments(convertToTaskCommentDTOs(task.getComments()));
        return taskResponseDTO;
    }

    private List<TaskCommentDTO> convertToTaskCommentDTOs(List<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    TaskCommentDTO taskCommentDTO = modelMapper.map(comment, TaskCommentDTO.class);
                    UserResponseDTO author = modelMapper.map(comment.getAuthor(), UserResponseDTO.class);
                    taskCommentDTO.setAuthor(author);
                    return taskCommentDTO;
                })
                .collect(Collectors.toList());
    }
}
