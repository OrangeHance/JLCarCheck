package com.mvp.service;

import com.mvp.dto.MesCarInfo;
import com.mvp.dto.QualityCheckSubmitDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface CarInfoService {

     MesCarInfo getCarByBarCode(String job);

    QualityCheckSubmitDTO findCarInfoAndcCheck(String job, String vin);
}
