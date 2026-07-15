package com.mvp.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mvp.common.Result;
import com.mvp.dto.MesCarInfo;
import com.mvp.dto.QualityCheckSubmitDTO;
import com.mvp.entity.CheckResult;
import com.mvp.entity.MesCarInfoHis;
import com.mvp.mapper.MesCarInfoHisMapper;
import com.mvp.service.CheckresultService;
import com.mvp.service.MesCarInfoHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/checkHis")
public class CheckHisController {

    @Autowired
    MesCarInfoHisService mesCarInfoHisService;

    @Autowired
    CheckresultService checkresultService;

    @Autowired
    MesCarInfoHisMapper mesCarInfoHisMapper;

    @PostMapping("/submit")
    public Result<String> submitCheck(@RequestBody QualityCheckSubmitDTO dto) {
        // 1. 当前登录人（这里模拟，实际从token上下文获取）
        String loginUser = "admin";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 前端传递岗位（路由meta.job）
        String job = dto.getJobCode();
        //先向历史表存数据
        MesCarInfoHis his = MesCarInfoHis.builder()
                .productSeqNum(dto.getCarInfo().getProductSeqNum())
                .productNum(dto.getCarInfo().getProductNum())
                .stationCode(dto.getCarInfo().getStationCode())
                .materialCode(dto.getCarInfo().getMaterialCode())
                .job(job)
                .creater(loginUser)
                .createTime(now)
                .build();
        int id = mesCarInfoHisService.insertCheckResult(his);

        // 2. 循环每条检查结果入库 checkresult
        for (QualityCheckSubmitDTO.CheckItemDTO item : dto.getCheckList()) {
            CheckResult result = new CheckResult();
            result.setJob(job);
            result.setItemId(item.getId().toString());
            result.setHisId(String.valueOf(id));
            // 0=NOK，1=OK
            result.setIsOk(String.valueOf(item.getResult()));
            result.setCreater(loginUser);
            result.setCreateTime(now);
            result.setUpdater(loginUser);
            result.setUpdateTime(now);
            checkresultService.insertResultItems(result);
        }

        // 3. 如需保存车辆历史记录 mescarinfohis，自行补充insert逻辑
        return Result.success("提交保存成功");
    }
}
