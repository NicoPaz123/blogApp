package com.example.blogApp.post.postservice;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blogApp.domain.Post;
import com.example.blogApp.dto.PostDto;
import com.example.blogApp.repo.PostRepo;
import com.example.blogApp.service.PostService;
import com.example.blogApp.utils.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PostService postService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createUsers() {
        try {
            String content = fileUtil.readFileFromResources("posts.json");
            List<PostDto> loadedPosts = mapper.readValue(content,new TypeReference<List<PostDto>>(){});
            List<PostDto> posts = loadedPosts.stream().map((post) -> postService.createPost(post)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
