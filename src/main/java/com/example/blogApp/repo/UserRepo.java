package com.example.blogApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogApp.domain.User;

public interface UserRepo extends JpaRepository<User,Integer> {

    
}