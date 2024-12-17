package com.taga.management.repository;

import com.taga.management.models.Task;
import com.taga.management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    ArrayList<Task> findByProjectId(Long projectId);
    ArrayList<Task> findByTitle(String title);

    Task getTaskById(Long taskId);

    List<Task> getTaskByAssignee(User assignee);
}
