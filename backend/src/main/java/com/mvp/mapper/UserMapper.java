package com.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mvp.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectLoginUser (@Param("userName") String userName);
}
