package com.taga.management.services.impl;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.Project;
import com.taga.management.models.ResponseEntity;
import com.taga.management.models.Task;
import com.taga.management.repository.ProjectRepository;
import com.taga.management.repository.TaskRepository;
import com.taga.management.converters.TaskConverter;
import com.taga.management.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {
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
            tasks = taskRepository.findByProjectId(projectId);
        }
        catch (Exception e){
            taskResponseDTOs = null;
            return taskResponseDTOs;
        }

        ArrayList<TaskResponseDTO> finalTaskResponseDTOs = taskResponseDTOs;
        tasks.stream().forEach(task -> {
            TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
            if (task.getComments() != null) {
                taskResponseDTO.setCommentNumber(task.getComments().size());
            } else {
                taskResponseDTO.setCommentNumber(0);
            }
            finalTaskResponseDTOs.add(taskResponseDTO);
        });
        return taskResponseDTOs;
    }


    @Override
    public ResponseEntity addOrUpdateTask(TaskInputDTO taskInputDTO) {
        ResponseEntity responseEntity = new ResponseEntity();
        Task task = taskConverter.convertToTask(taskInputDTO);
        try {
            Project project = projectRepository.findById(taskInputDTO.getProjectId())
                    .orElseThrow(EntityNotFoundException::new);
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
    public ArrayList<Task> findTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }


}
