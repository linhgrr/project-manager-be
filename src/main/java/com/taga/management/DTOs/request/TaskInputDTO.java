package com.taga.management.DTOs.request;

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

    String title;
    String description;
    String priority;
    Date dueDate;
    String status;
    Long projectId;
}
