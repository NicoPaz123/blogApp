package com.example.blogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.dto.UserDto;
import com.example.blogApp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        UserDto createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/create-users")
    public ResponseEntity<?> createUsers(){
        userService.createUsers();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{user-id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto updatedUser, @PathVariable(name = "user-id") Integer userId){
        UserDto user = userService.updateUser(updatedUser, userId);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping("/get-user/{user-id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "user-id") Integer userId){
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/get-users-sorted")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam (name = "field-name") String field){
        List<UserDto> allUsers = userService.getAllUsersSorted(field);
        return new ResponseEntity<>(allUsers,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/get-users-paginated-sorted")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam (name = "page-size") Integer pageSize,@RequestParam(name = "offset") Integer offset, @RequestParam(name = "field-name") String field){
        List<UserDto> allUsers = userService.getAllUsersPaginatedAndSorted(offset, pageSize,field);
        return new ResponseEntity<>(allUsers,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/delete-user/{user-id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable(name = "user-id") Integer id){
        UserDto deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser,HttpStatus.OK);
    }


}
