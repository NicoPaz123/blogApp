package com.example.blogApp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private List<PostDto> posts;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer pageCount;
    private Integer totalPosts;
    private boolean isLast;


    
}
