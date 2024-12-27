package com.example.blogApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Integer id;

    @NotEmpty(message = "Name is required!!!!")
    private String name;

    // TODO: Add validation for email
    @Email(message = "Email is not valid!!!")
    private String email;

    //TODO: Add validation for password
    @NotEmpty(message = "Password is required!!!!")
    private String password;
    
    private String about;
}
