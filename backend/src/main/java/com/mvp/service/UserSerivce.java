package com.mvp.service;

import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.UserDto;
import com.mvp.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface UserSerivce {
    LoginResponseDTO login (LoginRequestDTO loginRequestDTO);
    UserDto getUserInfo(String token);

}
