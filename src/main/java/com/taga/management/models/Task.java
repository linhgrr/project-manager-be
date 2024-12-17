package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assigned_user")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "task_image_url")
    String taskImageUrl;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SubTask> subTasks;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;
}

