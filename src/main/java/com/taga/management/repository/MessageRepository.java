package com.taga.management.repository;

import com.taga.management.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByProject_Id(Long id);
}
