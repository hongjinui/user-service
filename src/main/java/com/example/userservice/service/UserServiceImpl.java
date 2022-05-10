package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null){
            throw new UsernameNotFoundException(username);  // username(email) 일치하는 데이터가 없다면
        }

        // 이메일, 암호화된 패스워드, ..., 권한 배열로 User객체를 생성하여 리턴
        // 이메일 데이터 확인 후 암호화된 패스워드를 비교하여 로그인 처리
      return new User(userEntity.getEmail(), userEntity.getEncryptPwd()
                , true, true, true, true
                , new ArrayList<>()
        );

    }
}
