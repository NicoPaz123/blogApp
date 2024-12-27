package com.example.blogApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    
    private Integer id;

    @NotEmpty(message = "Title is required!!!!")
    private String title;

    @NotEmpty(message = "Description is required!!!!")
    private String description;
}
