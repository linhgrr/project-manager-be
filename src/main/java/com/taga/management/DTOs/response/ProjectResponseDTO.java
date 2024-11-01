package com.taga.management.DTOs.response;

import com.taga.management.DTOs.ManagerDTO;
import com.taga.management.DTOs.StaffDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProjectResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
    private List<ManagerDTO> managers;
    private List<StaffDTO> staffs;
    private boolean isManager;
    private String pictureUrl; 
}
