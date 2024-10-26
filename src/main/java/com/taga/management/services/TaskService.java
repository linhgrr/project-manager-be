package com.taga.management.services;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.Task;

import java.util.ArrayList;

public interface TaskService {
    // get all tasks of a project
    public ArrayList<TaskResponseDTO> getTaskOfProject(Long projectId);
    // add or update a task
    void addOrUpdateTask(TaskInputDTO taskInputDTO);
    // find tasks by title
    ArrayList<Task> findTasksByTitle(String title);
}
