package com.mvp.service;

import com.mvp.entity.CheckResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface CheckresultService {

    void insertResultItems(CheckResult result);
}
