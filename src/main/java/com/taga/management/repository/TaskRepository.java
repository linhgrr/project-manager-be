package com.taga.management.repository;

import com.taga.management.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;

public interface TaskRepository extends JpaRepository<Task, Long> {
    ArrayList<Task> findByProjectId(Long projectId);
    ArrayList<Task> findByTitle(String title);
}
