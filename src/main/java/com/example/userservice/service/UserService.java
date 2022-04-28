package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;

public interface UserService {

    UserDTO createdUser(UserDTO userDTO);

    Iterable<UserEntity> getUserByAll();

    UserEntity getUserByUserId(String userId);
}
