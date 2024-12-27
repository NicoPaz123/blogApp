package com.example.blogApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogApp.domain.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    
}
