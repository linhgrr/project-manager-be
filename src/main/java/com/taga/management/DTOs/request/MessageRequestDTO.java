package com.taga.management.DTOs.request;

import com.taga.management.models.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageRequestDTO {
    private Long id;
    private String content;
    private Long senderId;
    private LocalDateTime timestamp;
    private Long projectId;
    private String token;
}
