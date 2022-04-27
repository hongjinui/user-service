package com.example.userservice;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import com.example.userservice.service.UserServiceImpl;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private Greeting greeting;
    UserServiceImpl userService;

    @Autowired
    public UserController(Environment env, Greeting greeting,UserServiceImpl userService ){
        this.env = env;
        this.greeting = greeting;
        this.userService = userService;
    }

    @GetMapping(value = "/health_check")
    public String status(){
        return "It's working in user service";
    }

    @GetMapping(value = "/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping(value = "/users")
    public String createUser(@RequestBody RequestUser requestUser){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = mapper.map(requestUser, UserDTO.class);

        userService.createdUser(userDTO);

        return "Create User Success!";

    }

}
