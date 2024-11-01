package com.taga.management.DTOs.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String content;
    private Long senderId;
    private String fullName;
    private LocalDateTime timestamp;
}

