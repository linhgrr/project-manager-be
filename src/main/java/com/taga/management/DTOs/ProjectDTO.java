package com.taga.management.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ProjectDTO {
    @NotBlank(message = "Please enter title")
    private String title;
    private String description;
    @JsonProperty("startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("endDate")
    private Date endDate;
    @JsonProperty("pictureUrl")
    private String pictureUrl;
    private String status;
}
