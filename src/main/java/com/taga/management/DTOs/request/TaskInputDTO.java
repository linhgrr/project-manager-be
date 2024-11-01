package com.taga.management.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskInputDTO {
    Long id;
    @NotBlank(message = "please enter title for this task")
    String title;
    String description;
    String priority;
    Date dueDate;
    String status;
    Long projectId;
}
