package com.mvp.controller;

import com.mvp.common.Result;
import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.UserDto;
import com.mvp.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserSerivce userSerivce;


    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return Result.success(userSerivce.login(request));
    }

    @GetMapping("/info")
    public Result<UserDto> getUserInfo(@RequestHeader("Authorization") String token) {
        return Result.success(userSerivce.getUserInfo(token));
    }
}
