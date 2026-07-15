package com.mvp.controller;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.MesCarInfo;
import com.mvp.dto.QualityCheckSubmitDTO;
import com.mvp.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carInfo")
public class CarInfoController {


    @Autowired
    CarInfoService carInfoService;

    /**
     * 查询待检查车辆信息
     * @param job
     * @return
     */
    @GetMapping("/getCarByBarCode")
    public Result<MesCarInfo> getCarByBarCode(@RequestParam String job) {
        MesCarInfo info = carInfoService.getCarByBarCode(job);
        return Result.success(info);
    }


    /**
     * 根据vin和job查询车辆信息及检查状况
     * @param job
     * @return
     */
    @GetMapping("/findCarInfoAndcCheck")
    public Result<QualityCheckSubmitDTO> findCarInfoAndcCheck(@RequestParam String job, @RequestParam String vin) {
        QualityCheckSubmitDTO info = carInfoService.findCarInfoAndcCheck(job,vin);
        return Result.success(info);
    }
}
