package com.taga.management.DTOs.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserUpdateDTO {
    private Long id;
    private String username;
    private String fullName;
    private String address;
    private Date dateOfBirth;
    private String pictureUrl;
}
