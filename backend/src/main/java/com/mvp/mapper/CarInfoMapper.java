package com.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mvp.dto.MesCarInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarInfoMapper extends BaseMapper<MesCarInfo> {
    // 内置方法：selectById、insert、updateById、deleteById、selectList...
}
