package com.example.blogApp.service;

import java.util.List;

import com.example.blogApp.dto.UserDto;

public interface UserService {
    
    public UserDto createUser(UserDto userDto);

    public List<UserDto> createUsers();

    public UserDto getUserById(Integer id);

    public UserDto updateUser(UserDto user, Integer id);

    public List<UserDto> getAllUsers();

    public List<UserDto> getAllUsersSorted(String field);

    public UserDto deleteUser(Integer id);

    public List<UserDto> getAllUsersPaginatedAndSorted(Integer ofset, Integer pageSize, String field);

}
