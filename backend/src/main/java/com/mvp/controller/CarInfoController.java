package com.mvp.controller;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.MesCarInfo;
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
     * @param vin
     * @return
     */
    @GetMapping("/getCarByBarCode")
    public Result<MesCarInfo> getCarByBarCode(@RequestParam String job,@RequestParam String vin) {
        MesCarInfo info = carInfoService.getCarByBarCode(job,vin);
        return Result.success(info);
    }
}
