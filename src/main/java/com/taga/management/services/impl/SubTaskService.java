package com.taga.management.services.impl;

import com.taga.management.DTOs.response.SubTaskDTO;
import com.taga.management.converters.SubTaskConverter;
import com.taga.management.models.SubTask;
import com.taga.management.repository.SubTaskRepository;
import com.taga.management.services.ISubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskService implements ISubTaskService {
    @Autowired
    private SubTaskRepository subTaskRepository;
    @Autowired
    private SubTaskConverter subTaskConverter;

    @Override
    public List<SubTaskDTO> getSubTasksOfTask(Long taskId) {
        return subTaskRepository.findByTask_Id(taskId).
                stream().
                map(subTaskConverter::toSubTaskDTO).toList();
    }

    @Override
    public SubTaskDTO addOrUpdateSubTask(SubTaskDTO subTaskDTO) throws Exception {
        SubTask subTask = subTaskConverter.toSubTask(subTaskDTO);
        subTaskRepository.save(subTask);
        return subTaskDTO;
    }
}
