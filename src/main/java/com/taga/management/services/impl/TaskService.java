package com.taga.management.services.impl;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.exceptions.AccessDeniedException;
import com.taga.management.models.Project;
import com.taga.management.models.ResponseEntity;
import com.taga.management.models.Task;
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
    public ArrayList<TaskResponseDTO> getTaskOfProject(Long projectId) {
        ArrayList<Task> tasks;
        ArrayList<TaskResponseDTO> taskResponseDTOs = new ArrayList<>();
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(EntityNotFoundException::new);
            Long userId = SecurityUtils.getPrincipal().getId();
            boolean isManager = project.getManagers().stream().anyMatch(user -> user.getId().equals(userId));

            if (!isManager) {
                throw new AccessDeniedException("User does not have permission to assign staff to this project");
            }
            tasks = taskRepository.findByProjectId(projectId);
        }
        catch (Exception e){
            taskResponseDTOs = null;
            return taskResponseDTOs;
        }

        ArrayList<TaskResponseDTO> finalTaskResponseDTOs = taskResponseDTOs;
        return getTaskResponseDTOS(tasks, taskResponseDTOs, finalTaskResponseDTOs);
    }


    @Override
    public ResponseEntity addOrUpdateTask(TaskInputDTO taskInputDTO) {
        ResponseEntity responseEntity = new ResponseEntity();
        Task task = taskConverter.convertToTask(taskInputDTO);
        try {
            Project project = projectRepository.findById(taskInputDTO.getProjectId())
                    .orElseThrow(EntityNotFoundException::new);
            Long userId = SecurityUtils.getPrincipal().getId();
            boolean isManager = project.getManagers().stream().anyMatch(user -> user.getId().equals(userId));

            if (!isManager) {
                throw new AccessDeniedException("User does not have permission to assign staff to this project");
            }
            task.setProject(project);
        } catch (Exception e) {
            responseEntity.setMessage("Project not found");
            return responseEntity;
        }
        if (taskInputDTO.getProjectId() != null){
            responseEntity.setMessage("Successfully added task");
        }
        else {
            responseEntity.setMessage("Update task successfully");
        }
        taskRepository.save(task);
        return responseEntity;
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

    private ArrayList<TaskResponseDTO> getTaskResponseDTOS(ArrayList<Task> tasks, ArrayList<TaskResponseDTO> taskResponseDTOs, ArrayList<TaskResponseDTO> finalTaskResponseDTOs) {
        tasks.forEach(task -> {
            TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
            finalTaskResponseDTOs.add(taskResponseDTO);
        });
        return taskResponseDTOs;
    }
}
