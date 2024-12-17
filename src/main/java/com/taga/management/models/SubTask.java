package com.taga.management.models;

import com.taga.management.enums.SubTaskStatus;
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
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detailDescription;
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private SubTaskStatus status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}

