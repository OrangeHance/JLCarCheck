package com.mvp.service;

import com.mvp.entity.MesCarInfoHis;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface MesCarInfoHisService {

    int insertCheckResult(MesCarInfoHis his);
}
