package com.taga.management.DTOs.response;

import com.taga.management.enums.SubTaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubTaskDTO {
    private Long id;
    private String detailDescription;
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private SubTaskStatus status;
    private Long taskId;
}
