package com.taga.management.DTOs.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCommentDTO {
    Long id;
    Long taskId;
    UserResponseDTO author;
    String content;
    Date createdDate;
}
