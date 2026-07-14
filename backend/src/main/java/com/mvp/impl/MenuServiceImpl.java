package com.mvp.impl;

import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.MenuDto;
import com.mvp.mapper.MenuMapper;
import com.mvp.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<MenuDto> menuList() {
        List<MenuDto> list = menuMapper.menuList();
        return list;
    }
}
