package com.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mvp.dto.MesCarInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarInfoMapper extends BaseMapper<MesCarInfo> {

    List<MesCarInfo> selectByjob(String job);

}
