package com.taga.management.repository;

import com.taga.management.models.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findByTask_Id(Long taskId);
}
