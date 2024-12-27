package com.example.blogApp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PostDto {

    
    private Integer postId;

    @NotBlank(message = "userId is required!!!!")
    private Integer userId;

    @NotBlank(message = "categoryId is required!!!!")
    private Integer categoryId;

    @NotBlank(message = "title is required!!!!")
    private String title;

    @NotBlank(message = "content is required!!!!")
    private String content;

    // @NotBlank(message = "imageName is required!!!!")
    private String imageName;

    private Date createdDate;

    private Date lastUpdatedDate;

    private UserDto user;

    private CategoryDto category;

}
