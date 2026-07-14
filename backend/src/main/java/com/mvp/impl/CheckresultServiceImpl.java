package com.mvp.impl;

import com.mvp.entity.CheckResult;
import com.mvp.mapper.CheckresultMapper;
import com.mvp.mapper.MesCarInfoHisMapper;
import com.mvp.service.CheckresultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckresultServiceImpl implements CheckresultService {

    @Autowired
    CheckresultMapper checkresultMapper;

    @Override
    public void insertResultItems(CheckResult result) {
        checkresultMapper.insert(result);
    }
}
