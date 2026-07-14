package com.mvp.impl;

import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.UserDto;
import com.mvp.entity.User;
import com.mvp.exception.BusinessException;
import com.mvp.mapper.UserMapper;
import com.mvp.util.BcryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserSerivceImpl userSerivce;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void credentialsAreValid() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testUser");
        request.setPassword("password123");

        User user = new User();
        user.setUserId(1);
        user.setUserName("testUser");
        user.setPassword(BcryptUtil.encode("password123"));
        user.setEmail("test@example.com");
        user.setStatus(0);

        when(userMapper.selectLoginUser("testUser")).thenReturn(user);

        LoginResponseDTO response = userSerivce.login(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals(1, response.getUserId());
        assertEquals("testUser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    public void whenUserNotFound() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("unknownUser");
        request.setPassword("password123");

        when(userMapper.selectLoginUser("unknownUser")).thenReturn(null);

        assertThrows(BusinessException.class, () -> userSerivce.login(request));
    }

    @Test
    public void passwordIsIncorrect() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setUserId(1);
        user.setUserName("testUser");
        user.setPassword(BcryptUtil.encode("correctPassword"));
        user.setStatus(0);

        when(userMapper.selectLoginUser("testUser")).thenReturn(user);

        assertThrows(BusinessException.class, () -> userSerivce.login(request));
    }

    @Test
    public void userIsDisabled() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testUser");
        request.setPassword("password123");

        User user = new User();
        user.setUserId(1);
        user.setUserName("testUser");
        user.setPassword(BcryptUtil.encode("password123"));
        user.setStatus(1);

        when(userMapper.selectLoginUser("testUser")).thenReturn(user);

        assertThrows(BusinessException.class, () -> userSerivce.login(request));
    }

    @Test
    public void tokenIsValid() {
        String token = "Bearer " + com.mvp.util.JwtUtil.generateToken(1, "testUser");

        User user = new User();
        user.setUserId(1);
        user.setUserName("testUser");
        user.setEmail("test@example.com");

        when(userMapper.selectById(1L)).thenReturn(user);

        UserDto result = userSerivce.getUserInfo(token);

        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void tokenIsInvalid() {
        String token = "invalidToken";

        UserDto result = userSerivce.getUserInfo(token);

        assertNotNull(result);
        assertEquals("无效的token", result.getMsg());
    }

    @Test
    public void tokenDoesNotStartWithBearer() {
        String token = "someTokenWithoutBearer";

        UserDto result = userSerivce.getUserInfo(token);

        assertNotNull(result);
        assertEquals("无效的token", result.getMsg());
    }
}