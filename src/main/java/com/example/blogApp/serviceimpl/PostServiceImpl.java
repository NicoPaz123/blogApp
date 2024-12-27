package com.example.blogApp.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogApp.domain.Category;
import com.example.blogApp.domain.Post;
import com.example.blogApp.domain.User;
import com.example.blogApp.dto.CategoryDto;
import com.example.blogApp.dto.PostDto;
import com.example.blogApp.dto.PostResponseDto;
import com.example.blogApp.dto.UserDto;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.repo.CategoryRepo;
import com.example.blogApp.repo.PostRepo;
import com.example.blogApp.repo.UserRepo;
import com.example.blogApp.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;    

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    private ObjectMapper mapper = new ObjectMapper();

    public PostDto createPost(PostDto post) {
        
        User user = userRepo.findById(post.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "userId", post.getUserId()));
        Category category = categoryRepo.findById(post.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", post.getCategoryId()));
        Post newPost = new Post();
        newPost.setContent( post.getContent());
        newPost.setTitle(post.getTitle());
        newPost.setImageName(post.getImageName());
        newPost.setCreatedDate(new Date());
        newPost.setLastUpdatedDate(new Date());
        newPost.setCategory(category);
        newPost.setUser(user);
        newPost = postRepo.save(newPost);
        
        
        PostDto createdPost = mapper.convertValue(newPost, PostDto.class);
        createdPost.setCategoryId(post.getCategoryId());
        createdPost.setUserId(post.getUserId());
        createdPost.setUser(mapper.convertValue(user, UserDto.class));
        createdPost.setCategory(mapper.convertValue(category,CategoryDto.class));
        return createdPost;
    }

    public PostDto updatePost(PostDto post, Integer postId) {
        
        Post fetchedPost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Post updatedPost = new Post();
        updatedPost.setPostId(postId);
        updatedPost.setContent(post.getContent());
        updatedPost.setCreatedDate(fetchedPost.getCreatedDate() == null ? new Date() : fetchedPost.getCreatedDate());
        updatedPost.setImageName( post.getImageName());
        updatedPost.setLastUpdatedDate(new Date());
        updatedPost.setTitle(post.getTitle());
        updatedPost.setCategory(fetchedPost.getCategory());
        updatedPost.setUser(fetchedPost.getUser());
        Post savedPost = postRepo.save(updatedPost);
        return mapper.convertValue(savedPost,PostDto.class);
    }

    public PostDto getPostById(Integer postId) {
        Post fetchedPost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        PostDto fetchedPostDto = mapper.convertValue(fetchedPost, PostDto.class);
        UserDto userDto = mapper.convertValue(fetchedPost.getUser(),UserDto.class); 
        CategoryDto categoryDto = mapper.convertValue(fetchedPost.getCategory(),CategoryDto.class);
        fetchedPostDto.setCategory(categoryDto);
        fetchedPostDto.setUser(userDto);
        return fetchedPostDto;
    }

    public PostResponseDto getAllPosts(Integer pageSize, Integer ofset, String sortBy, String sortDir) {
        Pageable p = null;
        if(sortDir.equalsIgnoreCase("desc")){
            p = PageRequest.of(ofset, pageSize).withSort(Sort.by(sortBy).descending());
        }
        else{
            p = PageRequest.of(ofset, pageSize).withSort(Sort.by(sortBy).ascending());
        }
        Page<Post> fetchedPosts = postRepo.findAll(p);
        List<Post> posts = fetchedPosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapper.convertValue(post, PostDto.class)).collect(Collectors.toList());
        PostResponseDto postResponse = new PostResponseDto();
        postResponse.setPosts(postDtos);
        postResponse.setPageCount(fetchedPosts.getTotalPages());
        postResponse.setPageNumber(fetchedPosts.getNumber());
        postResponse.setTotalPosts((int)fetchedPosts.getTotalElements());
        postResponse.setLast(fetchedPosts.isLast());
        postResponse.setPageSize(fetchedPosts.getSize());
        return postResponse;
    }

    public PostDto deletePost(Integer postId) {
        Post fetchedPost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        postRepo.delete(fetchedPost);
        return mapper.convertValue(fetchedPost, PostDto.class);
    }

    public PostResponseDto getPostsByUser(Integer userId, Integer pageSize, Integer ofset, String sortBy, String sortDir) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Pageable p = null;
        if(sortDir.equalsIgnoreCase("desc")){
            p = PageRequest.of(ofset, pageSize).withSort(Sort.by(sortBy).descending());
        }
        else{
            p = PageRequest.of(ofset, pageSize).withSort(Sort.by(sortBy).ascending());
        }
        Page<Post> fetchedPosts = postRepo.findByUser(user,p);
        
        List<PostDto> fetchedPostDtos = fetchedPosts.getContent().stream().map((post) -> mapper.convertValue(post,PostDto.class)).collect(Collectors.toList());

        PostResponseDto postResponse = new PostResponseDto();
        postResponse.setPosts(fetchedPostDtos);
        postResponse.setPageCount(fetchedPosts.getTotalPages());
        postResponse.setPageNumber(fetchedPosts.getNumber());
        postResponse.setTotalPosts((int)fetchedPosts.getTotalElements());
        postResponse.setLast(fetchedPosts.isLast());
        postResponse.setPageSize(fetchedPosts.getSize());
        
        return postResponse;
    }

    public PostResponseDto getPostsByCategory(Integer categoryId, Integer pageSize, Integer ofset, String sortBy, String sortDir) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Pageable p = PageRequest.of(ofset,pageSize).withSort(Sort.by(sortBy).ascending());
        Page<Post> fetchedPosts = postRepo.findByCategory(category,p);
        List<PostDto> fetchedPostDtos = fetchedPosts.getContent().stream().map((post) -> mapper.convertValue(post,PostDto.class)).collect(Collectors.toList());

        PostResponseDto postResponse = new PostResponseDto();
        postResponse.setPosts(fetchedPostDtos);
        postResponse.setPageCount(fetchedPosts.getTotalPages());
        postResponse.setPageNumber(fetchedPosts.getNumber());
        postResponse.setTotalPosts((int)fetchedPosts.getTotalElements());
        postResponse.setLast(fetchedPosts.isLast());
        postResponse.setPageSize(fetchedPosts.getSize());
        
        return postResponse;
    }

    public List<PostDto> searchPosts(String keyword){
        List<Post> posts = postRepo.findByTitleContains(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> mapper.convertValue(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    public String postImage(String path,MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString()+fileName.substring(fileName.indexOf('.'));
        String newFilePath = path+File.separator+uniqueFileName;
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(),Paths.get(newFilePath));
        return fileName;
    }

    public InputStream getImage(String path,String imageName) throws FileNotFoundException {
        String filePath = path+File.separator+imageName;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }
}
