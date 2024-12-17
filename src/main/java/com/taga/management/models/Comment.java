package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comment")
public class Comment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @Column(name = "content")
    String content;

    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;
}

