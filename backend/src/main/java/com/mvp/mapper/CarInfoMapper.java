package com.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mvp.dto.MesCarInfo;
import com.mvp.dto.QualityCheckSubmitDTO;
import com.mvp.entity.CheckResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarInfoMapper extends BaseMapper<MesCarInfo> {

    List<MesCarInfo> selectByjob(String job);

    List<CheckResult> findCarInfoAndcCheck(String job, String vin);
}
