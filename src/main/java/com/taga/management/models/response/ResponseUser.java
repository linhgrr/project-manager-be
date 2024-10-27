package com.taga.management.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUser {
    private Long id;
    private String username;
    private String fullName;
    private String pictureUrl;
}
