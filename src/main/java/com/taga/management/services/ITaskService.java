package com.taga.management.services;

import com.taga.management.DTOs.request.TaskInputDTO;
import com.taga.management.DTOs.response.TaskResponseDTO;
import com.taga.management.models.ResponseEntity;
import java.util.List;

public interface ITaskService {
    // get all tasks of a project
    List<TaskResponseDTO> getTaskOfProject(Long projectId);
    // add or update a task
    ResponseEntity addOrUpdateTask(TaskInputDTO taskInputDTO);
    // find tasks by title
    List<TaskResponseDTO> findTasksByTitle(String title);
    // delete a task
    ResponseEntity deleteTask(Long projectId, Long taskId);
}
