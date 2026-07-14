package com.mvp.impl;

import com.mvp.controller.CheckItemsController;
import com.mvp.dto.CarCheckItems;
import com.mvp.mapper.CheckInfoMapper;
import com.mvp.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckInfoMapper checkinfoMapper;

    @Override
    public List<CarCheckItems> searchItemByJob(String job) {
        List<CarCheckItems> items = checkinfoMapper.selectItemsByJob(job);
        return items;
    }
}
