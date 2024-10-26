package com.taga.management.services.impl;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.models.Project;
import com.taga.management.models.Task;
import com.taga.management.repository.TaskRepository;
import com.taga.management.services.TaskConverter;
import com.taga.management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;


    @Override
    public ArrayList<Task> getTaskOfProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }


    @Override
    public void addOrUpdateTask(TaskInputDTO taskInputDTO) {
        Task task = TaskConverter.convertToTask(taskInputDTO);
        Project project = new Project();
        try {
        project.setId(taskInputDTO.getProjectId());}
        catch (Exception e){
            System.out.println("Update notification");
        }
        // done after cc dont project repo
        // taskRepository.findById(projectId).get();
        // task.setProject(project);
        System.out.println("Add task notification");
        taskRepository.save(task);
    }

    @Override
    public ArrayList<Task> findTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }


}
