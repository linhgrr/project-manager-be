package com.taga.management.models.response;

import com.taga.management.DTOs.ManagerDTO;
import com.taga.management.models.Project;
import com.taga.management.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResponseProject {
    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
    private List<ManagerDTO> managers;
    private List<User> staffs;
    private boolean isManager;
    private String pictureUrl;
}
