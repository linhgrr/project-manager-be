package com.taga.management.services;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.ResponseEntity;
import com.taga.management.models.Task;

import java.util.ArrayList;

public interface TaskService {
    // get all tasks of a project
    public ArrayList<TaskResponseDTO> getTaskOfProject(Long projectId);
    // add or update a task
    ResponseEntity addOrUpdateTask(TaskInputDTO taskInputDTO);
    // find tasks by title
    ArrayList<TaskResponseDTO> findTasksByTitle(String title);
    // delete a task
    ResponseEntity deleteTask(Long projectId, Long taskId);
}
