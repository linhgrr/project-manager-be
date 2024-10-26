package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name="start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "task")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "project_manager",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private List<User> managers;
}
