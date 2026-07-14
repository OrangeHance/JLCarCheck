package com.mvp.impl;

import com.mvp.common.Result;
import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.UserDto;
import com.mvp.entity.User;
import com.mvp.exception.BusinessException;
import com.mvp.mapper.UserMapper;
import com.mvp.service.UserSerivce;
import com.mvp.util.BcryptUtil;
import com.mvp.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerivceImpl implements UserSerivce {

    @Autowired
    UserMapper userMapper;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = new LoginResponseDTO();
        User user = userMapper.selectLoginUser(loginRequestDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误"); // 自动返回500

        }
        if (!BcryptUtil.matches(loginRequestDTO.getPassword(),user.getPassword())) {
            throw new BusinessException("用户名或密码错误"); // 自动返回500

        }
        if (user.getStatus() == 1) {
            throw new BusinessException("用户名或密码错误"); // 自动返回500
        }
        String token = JwtUtil.generateToken(user.getUserId(), user.getUserName());
        response.setToken(token);
        response.setUserId(user.getUserId());
        response.setUsername(user.getUserName());
        response.setEmail(user.getEmail());
        response.setExpiresIn(3600L);
        return response;
    }

    @Override
    public UserDto getUserInfo(String token) {
        UserDto user = new UserDto();
        if (token == null || !token.startsWith("Bearer ")) {
            user.setMsg("无效的token");
            return user;
        }

        String jwtToken = token.substring(7);

        if (!JwtUtil.validateToken(jwtToken)) {
            user.setMsg("token已过期或无效");
            return user;
        }

        Long userId = JwtUtil.getUserIdFromToken(jwtToken);
        User userInfo = userMapper.selectById(userId);

        BeanUtils.copyProperties(userInfo,user);
        if (userInfo == null) {
            user.setMsg("用户不存在");
            return user;
        }
        return user;
    }
}
