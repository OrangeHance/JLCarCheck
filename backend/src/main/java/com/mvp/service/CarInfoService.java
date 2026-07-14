package com.mvp.service;

import com.mvp.dto.MesCarInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface CarInfoService {

     MesCarInfo carCheckSearch(MesCarInfo mesCarInfo);
}
