package com.taga.management.services;

import com.taga.management.DTOs.response.SubTaskDTO;

import java.util.List;

public interface ISubTaskService {
    List<SubTaskDTO> getSubTasksOfTask(Long taskId);
    SubTaskDTO addOrUpdateSubTask(SubTaskDTO subTaskDTO) throws Exception;
}
