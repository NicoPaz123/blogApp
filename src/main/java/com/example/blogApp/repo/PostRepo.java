package com.example.blogApp.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogApp.domain.Category;
import com.example.blogApp.domain.Post;
import com.example.blogApp.domain.User;


public interface PostRepo extends JpaRepository<Post,Integer>{
    
    Page<Post> findByUser(User user, Pageable p);

    Page<Post> findByCategory(Category category, Pageable p);

    List<Post> findByTitleContains(String keyword);
}
