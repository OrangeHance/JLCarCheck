package com.mvp.service;

import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.MenuDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public interface MenuService {
    List<MenuDto> menuList ();
}
