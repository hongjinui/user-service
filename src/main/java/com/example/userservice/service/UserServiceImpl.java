package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createdUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userEntity.setEncryptPwd(passwordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);

        return null;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {

        Iterable<UserEntity> userList = userRepository.findAll();

        return userList;

    }

    @Override
    public UserEntity getUserByUserId(String userId) {

        UserEntity user = userRepository.findByUserId(userId);

        return user;

    }
}
