package com.taga.management.services.impl;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.models.Project;
import com.taga.management.models.ResponseEntity;
import com.taga.management.models.Task;
import com.taga.management.models.User;
import com.taga.management.repository.ProjectRepository;
import com.taga.management.repository.TaskRepository;
import com.taga.management.converters.TaskConverter;
import com.taga.management.services.ITaskService;
import com.taga.management.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TaskResponseDTO> getTaskOfProject(Long projectId) {
        ArrayList<Task> tasks;
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new EntityNotFoundException("Project not found"));
            Long userId = SecurityUtils.getPrincipal().getId();
            boolean isStaff = project.getStaffs().stream().anyMatch(user -> user.getId().equals(userId));

            if (!isStaff) {
                throw new AccessDeniedException("You do not have permission to get tasks of this project");
            }

            tasks = taskRepository.findByProjectId(projectId);
            return taskConverter.toTaskResponseDTOs(tasks);
        } catch (AccessDeniedException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching tasks", e);
        }
    }


    @Override
    public TaskResponseDTO addOrUpdateTask(TaskInputDTO taskInputDTO) {
        Task task = taskConverter.convertToTask(taskInputDTO);

        Project project = task.getProject();
        Long creatorId = task.getCreator().getId();
        boolean isManager = project.getManagers().stream().anyMatch(user -> user.getId().equals(creatorId));
        if (!isManager) {
            throw new AccessDeniedException("User does not have permission to add task");
        }

        taskRepository.save(task);

        return taskConverter.toTaskResponseDTO(task);
    }

    @Override
    public ArrayList<TaskResponseDTO> findTasksByTitle(String title) {
        ArrayList<Task> tasks =  taskRepository.findByTitle(title);
        if (tasks == null) {
            return null;
        }
        ArrayList<TaskResponseDTO> taskResponseDTOs = new ArrayList<>();
        return getTaskResponseDTOS(tasks, taskResponseDTOs, taskResponseDTOs);
    }

    @Override
    public ResponseEntity deleteTask(Long projectId, Long taskId) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
            if (Objects.equals(task.getProject().getId(), projectId)) {
                taskRepository.delete(task);
                responseEntity.setMessage("Successfully deleted task");
            } else {
                responseEntity.setMessage("Task not found");
            }
        } catch (Exception e) {
            responseEntity.setMessage("Task not found");
        }
        return responseEntity;
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        User user = SecurityUtils.getPrincipal();
        List<Task> tasks = taskRepository.getTaskByAssignee(user);

        return taskConverter.toTaskResponseDTOs(tasks);
    }

    private ArrayList<TaskResponseDTO> getTaskResponseDTOS(ArrayList<Task> tasks, ArrayList<TaskResponseDTO> taskResponseDTOs, ArrayList<TaskResponseDTO> finalTaskResponseDTOs) {
        tasks.forEach(task -> {
            TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
            finalTaskResponseDTOs.add(taskResponseDTO);
        });
        return taskResponseDTOs;
    }
}
