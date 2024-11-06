package com.taga.management.DTOs.response;

import com.taga.management.DTOs.AssigneeDTO;
import com.taga.management.DTOs.ManagerDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;

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
    AssigneeDTO assignee;
    ManagerDTO creator;
    Date dueDate;
    Date startDate;
    String priority;
    String status;
    Date createdDate;
    String taskImageUrl;
    List<TaskCommentDTO> comments;
    // chua co task detail
}
