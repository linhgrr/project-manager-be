package com.taga.management.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Data //toString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("username")
    @NotBlank(message = "Username is required")
    private String username;

    private String address;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("picture_url")
    private String pictureUrl;
}
