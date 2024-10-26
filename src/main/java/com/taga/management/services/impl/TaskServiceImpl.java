package com.taga.management.services.impl;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.Project;
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
        ArrayList<Task> tasks = taskRepository.findByProjectId(projectId);
        ArrayList<TaskResponseDTO> taskResponseDTOs = new ArrayList<>();
        tasks.stream().forEach(task -> {
            TaskResponseDTO taskResponseDTO = modelMapper.map(task, TaskResponseDTO.class);
            if (task.getComments() != null) {
                taskResponseDTO.setCommentNumber(task.getComments().size());
            } else {
                taskResponseDTO.setCommentNumber(0);
            }
            taskResponseDTOs.add(taskResponseDTO);

        });
        return taskResponseDTOs;
    }


    @Override
    public void addOrUpdateTask(TaskInputDTO taskInputDTO) {
        Task task = taskConverter.convertToTask(taskInputDTO);
        try {
            Project project = projectRepository.findById(taskInputDTO.getProjectId()).orElse(null);
            task.setProject(project);
        } catch (Exception e) {
            System.out.println("Project not found");
            return;
        }
        System.out.println("Add task notification");
        taskRepository.save(task);
    }

    @Override
    public ArrayList<Task> findTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }


}
