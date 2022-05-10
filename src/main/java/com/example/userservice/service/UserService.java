package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createdUser(UserDTO userDTO);

    Iterable<UserEntity> getUserByAll();

    UserEntity getUserByUserId(String userId);
}
