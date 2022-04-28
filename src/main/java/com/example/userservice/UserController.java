package com.example.userservice;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserServiceImpl;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    private Environment env;
    private Greeting greeting;
    private UserServiceImpl userService;

    @Autowired
    public UserController(Environment env, Greeting greeting,UserServiceImpl userService ){
        this.env = env;
        this.greeting = greeting;
        this.userService = userService;
    }

    @GetMapping(value = "/health_check")
    public String status(){

        return String.format("It's working in user service on PORT %s",env.getProperty("local.server.port"));
    }

    @GetMapping(value = "/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }



    @PostMapping(value = "/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = mapper.map(requestUser, UserDTO.class);

        userService.createdUser(userDTO);

        ResponseUser responseUser = mapper.map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){

        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add( new ModelMapper().map(v, ResponseUser.class) );
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){

        UserEntity user = userService.getUserByUserId(userId);

        ResponseUser result = new ModelMapper().map(user, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
