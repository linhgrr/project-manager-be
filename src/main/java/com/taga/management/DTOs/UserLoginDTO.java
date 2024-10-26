package com.taga.management.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    @JsonProperty("username")
    @NotBlank(message = "Please enter username")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Please enter password")
    private String password;
}
