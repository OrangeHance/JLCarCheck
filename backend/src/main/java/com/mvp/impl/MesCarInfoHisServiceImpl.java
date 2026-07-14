package com.mvp.impl;

import com.mvp.entity.MesCarInfoHis;
import com.mvp.mapper.MesCarInfoHisMapper;
import com.mvp.service.MesCarInfoHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MesCarInfoHisServiceImpl implements MesCarInfoHisService {
    @Autowired
    MesCarInfoHisMapper mapper;

    @Override
    public int insertCheckResult(MesCarInfoHis his) {
        int id = mapper.insert(his);
        return 0;
    }
}
