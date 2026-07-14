package com.mvp.controller;

import com.mvp.common.Result;
import com.mvp.dto.MenuDto;
import com.mvp.dto.UserDto;
import com.mvp.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menuList")
    public Result<List<MenuDto>> menuList() {
        return Result.success(menuService.menuList());
    }
}
