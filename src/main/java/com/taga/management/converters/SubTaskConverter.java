package com.taga.management.converters;

import com.taga.management.DTOs.response.SubTaskDTO;
import com.taga.management.models.SubTask;
import com.taga.management.models.Task;
import com.taga.management.repository.SubTaskRepository;
import com.taga.management.services.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubTaskConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private SubTaskRepository subTaskRepository;
    public SubTask toSubTask(SubTaskDTO subTaskDTO) {
        SubTask subTask = modelMapper.map(subTaskDTO, SubTask.class);
        Long id = subTaskDTO.getTaskId();

        // check all subtasks of the task: if no subtask is in progress, set the task "To do" status, else if all subtasks are done, set the task "Done" status, else if there is at least one subtask in progress, set the task "In progress" status
        List<SubTask> subTasks = subTaskRepository.findByTask_Id(id);
        int count = 0;

        for (SubTask task : subTasks) {
            if (subTaskDTO.getId() != null && subTaskDTO.getId().equals(task.getId())) {
                task.setStatus(subTaskDTO.getStatus());
            }

            String taskStatus = String.valueOf(task.getStatus());

            if (taskStatus.equals("COMPLETED")) {
                count++;
            }
        }

        Task task = taskService.getTaskById(id);
        if (count == 0){
            task.setStatus("To do");
        } else if (count == subTasks.size()) {
            task.setStatus("In review");
        } else {
            task.setStatus("In progress");
        }

        subTask.setTask(task);
        return subTask;
    }

    public SubTaskDTO toSubTaskDTO(SubTask subTask) {
        SubTaskDTO subTaskDTO = modelMapper.map(subTask, SubTaskDTO.class);
        subTaskDTO.setTaskId(subTask.getTask().getId());
        return subTaskDTO;
    }
}
