package com.example.blogApp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogApp.dto.PostDto;
import com.example.blogApp.dto.PostResponseDto;
import com.example.blogApp.service.FileService;
import com.example.blogApp.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/apis/posts")
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${blogApp.image}")
    private String path;

    @PostMapping("/create-post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto createdPost = postService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/get-post/{post-id}")
    public ResponseEntity<PostDto> getPost(@PathVariable(name = "post-id") Integer postId){
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/get-posts")
    public ResponseEntity<PostResponseDto> getPosts(@RequestParam(name = "page-size", defaultValue = "5", required = false) Integer pageSize, 
                        @RequestParam(defaultValue = "0",required = false) Integer ofset, 
                        @RequestParam(defaultValue = "postId",required = false) String sortBy, 
                        @RequestParam(defaultValue = "asc",required = false) String sortDir){
        PostResponseDto posts = postService.getAllPosts(pageSize,ofset,sortBy,sortDir);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/user/{user-id}/get-posts")
    public ResponseEntity<PostResponseDto> getPostByUser(@PathVariable (name = "user-id") Integer userId,
            @RequestParam(name = "page-size", defaultValue = "5", required = false) Integer pageSize, 
            @RequestParam(defaultValue = "0",required = false) Integer ofset, 
            @RequestParam(defaultValue = "postId",required = false) String sortBy, 
            @RequestParam(defaultValue = "asc",required = false) String sortDir){
        PostResponseDto fetchedPosts = postService.getPostsByUser(userId,pageSize,ofset,sortBy,sortDir);
        return new ResponseEntity<>(fetchedPosts,HttpStatus.OK);
    }

    @GetMapping("/category/{category-id}/get-posts")
    public ResponseEntity<PostResponseDto> getPostByCategory(@PathVariable (name = "category-id") Integer categoryId,
                @RequestParam(name = "page-size", defaultValue = "5", required = false) Integer pageSize, 
                @RequestParam(defaultValue = "0",required = false) Integer ofset, 
                @RequestParam(defaultValue = "postId",required = false) String sortBy, 
                @RequestParam(defaultValue = "asc",required = false) String sortDir){
        PostResponseDto fetchedPosts = postService.getPostsByCategory(categoryId,pageSize,ofset,sortBy,sortDir);
        return new ResponseEntity<>(fetchedPosts,HttpStatus.OK);
    }

    @GetMapping("/update-post/{post-id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto post,@PathVariable(name = "post-id")Integer postId){
        PostDto updatedPost = postService.updatePost(post,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.ACCEPTED);
    }

    @GetMapping("/search-post/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable(name = "keyword") String keyword){
        List<PostDto> posts = postService.searchPosts(keyword);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> postImage(@RequestParam(name = "image") MultipartFile file){
        String response = null;
        try {
            response = postService.postImage(path,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value= "/download-image/{image-name}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable(name = "image-name") String imageName,
        HttpServletResponse response
    ) throws IOException{
        InputStream content = postService.getImage(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(content, response.getOutputStream());
    }

    @PostMapping("/upload-image/{post-id}")
    public ResponseEntity<PostDto> uploadImage(@PathVariable(name="post-id") Integer postId, @RequestParam(name = "image") MultipartFile file) throws IOException{
        PostDto fetchPost = postService.getPostById(postId);
        String fileName = fileService.addFileToPost(path, file);
        fetchPost.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(fetchPost,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

}
