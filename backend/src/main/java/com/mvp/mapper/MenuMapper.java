package com.mvp.mapper;

import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuDto> menuList ();

}
