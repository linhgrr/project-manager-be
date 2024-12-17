package com.taga.management.DTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private String address;
    private Date dateOfBirth;
    private String pictureUrl;
    private boolean isSubscribed;
}
