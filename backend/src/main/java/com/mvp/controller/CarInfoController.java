package com.mvp.controller;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.MesCarInfo;
import com.mvp.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/WareHouseIn")
public class CarInfoController {


    @Autowired
    CarInfoService carInfoService;

    /**
     * 查询待检查车辆信息
     * @param mesCarInfo
     * @return
     */
    @PostMapping("/carCheckSearch")
    public Result<MesCarInfo> carCheckSearch(@RequestBody MesCarInfo mesCarInfo) {
        return Result.success(carInfoService.carCheckSearch(mesCarInfo));
    }
}
