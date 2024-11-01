package com.taga.management.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String token;
}
