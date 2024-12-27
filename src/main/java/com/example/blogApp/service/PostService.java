package com.example.blogApp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.blogApp.dto.PostDto;
import com.example.blogApp.dto.PostResponseDto;


public interface PostService {

    public PostDto createPost(PostDto post);

    public PostDto updatePost(PostDto post, Integer postId);

    public PostDto getPostById(Integer postId);

    public PostResponseDto getAllPosts(Integer pageSize, Integer ofset, String sortBy, String sortDir);

    public PostDto deletePost(Integer postId);

    public PostResponseDto getPostsByUser(Integer userId, Integer pageSize, Integer ofset, String sortBy, String sortDir);

    public PostResponseDto getPostsByCategory(Integer categoryId, Integer pageSize, Integer ofset, String sortBy, String sortDir);

    public List<PostDto> searchPosts(String keyword);

    public String postImage(String path,MultipartFile file) throws IOException;

    public InputStream getImage(String path,String imageName) throws FileNotFoundException;
}
