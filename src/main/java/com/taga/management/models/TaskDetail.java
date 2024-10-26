package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "taskdetail")
public class TaskDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detailDescription;
    private Date createdDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    // Getters and Setters
}

