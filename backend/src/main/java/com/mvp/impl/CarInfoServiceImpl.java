package com.mvp.impl;


import com.mvp.dto.MesCarInfo;
import com.mvp.mapper.CarInfoMapper;
import com.mvp.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    CarInfoMapper carInfoMapper;

    @Override
    public MesCarInfo getCarByBarCode(String job, String vin) {
        //根据job查询需要检查的车辆信息
        List<MesCarInfo> infos = carInfoMapper.selectByjob(job);
        return infos.get(0);
    }
}
