package com.example.blogApp.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogApp.domain.User;
import com.example.blogApp.dto.UserDto;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.repo.UserRepo;
import com.example.blogApp.service.UserService;
import com.example.blogApp.utils.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileUtil fileUtil;

    private static ObjectMapper mapper = new ObjectMapper();

    public UserDto createUser(UserDto userDto) {
        User user = mapper.convertValue(userDto,User.class);
        userRepo.save(user);
        UserDto savedUser = mapper.convertValue(user,UserDto.class);
        return savedUser;
    }

    public UserDto getUserById(Integer id) {
        User fetchedUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "userId", id));
        UserDto fetchUserDto = mapper.convertValue(fetchedUser,UserDto.class);
        return fetchUserDto;
    }

    public UserDto updateUser(UserDto user, Integer id) {
        User fetchedUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "userId", id));
        if(fetchedUser == null){
            throw new ResourceNotFoundException("User", "userId", id);
        }
        fetchedUser.setName(user.getName());
        fetchedUser.setEmail(user.getEmail());
        fetchedUser.setAbout(user.getAbout());
        fetchedUser.setPassword(user.getPassword());
        User updatedUser = userRepo.save(fetchedUser);
        return mapper.convertValue(updatedUser,UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<UserDto> fetchedUsers = mapper.convertValue(allUsers,new TypeReference<List<UserDto>>(){});
        return fetchedUsers;
    }

    public List<UserDto> getAllUsersSorted(String field){
        List<User> sortedUsers = userRepo.findAll(Sort.by(field).ascending());
        List<UserDto> result = sortedUsers.stream().map((user) -> mapper.convertValue(user,UserDto.class)).collect(Collectors.toList());
        return result;
    }

    public List<UserDto> getAllUsersPaginatedAndSorted(Integer ofset, Integer pageSize, String field){
        List<User> sortedUsers = mapper.convertValue(userRepo.findAll(PageRequest.of(ofset, pageSize).withSort(Sort.by(field))).getContent(),new TypeReference<>(){});
        List<UserDto> result = sortedUsers.stream().map((user) -> mapper.convertValue(user,UserDto.class)).collect(Collectors.toList());
        return result;
    }

    public UserDto deleteUser(Integer id) {
        User fetchedUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "userId", id));
        if(fetchedUser == null){
            throw new ResourceNotFoundException("User", "userId", id);
        }
        userRepo.delete(fetchedUser);
        UserDto deletedUser = mapper.convertValue(fetchedUser,UserDto.class);
        return deletedUser;
    }

    @Override
    public List<UserDto> createUsers() {
        try {
            String content = fileUtil.readFileFromResources("users.json");
            List<User> loadedUsers = mapper.readValue(content,new TypeReference<List<User>>(){});
            userRepo.saveAll(loadedUsers);
            return mapper.convertValue(loadedUsers, new TypeReference<List<UserDto>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
