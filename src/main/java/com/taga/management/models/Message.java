package com.taga.management.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "sender_id")
    private Long senderId;
    private LocalDateTime timestamp;
    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String content, Long senderId) {
        this.content = content;
        this.senderId = senderId;
        this.timestamp = LocalDateTime.now();
    }
}
