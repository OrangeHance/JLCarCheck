package com.mvp.impl;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mvp.dto.MesCarInfo;
import com.mvp.dto.QualityCheckSubmitDTO;
import com.mvp.entity.CheckResult;
import com.mvp.entity.MesCarInfoHis;
import com.mvp.mapper.CarInfoMapper;
import com.mvp.mapper.CheckresultMapper;
import com.mvp.mapper.MesCarInfoHisMapper;
import com.mvp.service.CarInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    CarInfoMapper carInfoMapper;

    @Autowired
    MesCarInfoHisMapper hisMapper;

    @Override
    public MesCarInfo getCarByBarCode(String job) {
        //根据job查询需要检查的车辆信息
        List<MesCarInfo> infos = carInfoMapper.selectByjob(job);
        return infos.get(0);
    }

    @Override
    public QualityCheckSubmitDTO findCarInfoAndcCheck(String job, String vin) {
        QualityCheckSubmitDTO dto = new QualityCheckSubmitDTO();
        //根据vin查询车辆信息
        MesCarInfo info = carInfoMapper.selectOne(
                Wrappers.lambdaQuery(MesCarInfo.class)
                        .eq(MesCarInfo::getProductNum, vin)
        );
        //查询最晚的历史记录
        List<MesCarInfoHis> his = hisMapper.selectList(
                Wrappers.lambdaQuery(MesCarInfoHis.class)
                        .eq(MesCarInfoHis::getProductNum, vin)
                        .orderByDesc(MesCarInfoHis::getCreateTime)
        );
        //根据vin查询是否有检查结果
        List<CheckResult> result = carInfoMapper.findCarInfoAndcCheck(job,vin);

        List<CheckResult> filterList = result.stream()
                .filter(item -> item.getHisId() != his.get(0).getId().toString())
                .collect(Collectors.toList());

        QualityCheckSubmitDTO.CarInfoDTO  carInfoDTO = new QualityCheckSubmitDTO.CarInfoDTO();
        if (ObjectUtils.isNotEmpty(info)){
            BeanUtils.copyProperties(info,carInfoDTO);
            dto.setCarInfo(carInfoDTO);
        }
        dto.setCarInfo(carInfoDTO);
        List<QualityCheckSubmitDTO.CheckItemDTO> checkList =  new ArrayList<>();
        for (CheckResult checkResult : filterList) {
            QualityCheckSubmitDTO.CheckItemDTO  checkItemDTO = new QualityCheckSubmitDTO.CheckItemDTO();
            checkItemDTO.setId(Integer.valueOf(checkResult.getItemId()));
            checkItemDTO.setResult(Integer.valueOf(checkResult.getIsOk()));
            checkList.add(checkItemDTO);
        }
        dto.setCheckList(checkList);
        return dto;
    }
}
