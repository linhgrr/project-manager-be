package com.taga.management.repository;

import com.taga.management.DTOs.AssignStaffDTO;
import com.taga.management.models.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO project_staff (project_id, staff_id) VALUES (:#{#dto.projectId}, :#{#dto.staffId})", nativeQuery = true)
    void assignStaff(@Param("dto") AssignStaffDTO assignStaffDTO);
}
