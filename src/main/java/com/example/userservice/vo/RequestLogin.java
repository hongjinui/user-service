package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    // 로그인 할 때 전달 받는 데이터
    @NotNull(message = "email can not be null")
    @Size(min = 2, message = "email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "password can not be null")
    @Size(min = 8, message = "password must be equals or grater than 8 characters ")
    private String password;

}
