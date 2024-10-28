package com.taga.management.DTOs.response;

import com.taga.management.models.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponseDTO {
    Long id;

    String title;
    String description;
    String priority;
    Date dueDate;
    String status;
    Integer commentNumber;
    ArrayList<Comment> comments;
}
