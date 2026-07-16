package com.mvp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mvp.dto.*;
import com.mvp.mapper.MesCarInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackPullScheduleTask {

    private final RestTemplate restTemplate;

    // 接口地址配置在yml
    private final String TRACK_API_URL = "";

    @Autowired
    MesCarInfoMapper mesCarInfoMapper;

    /**
     * 定时拉取T00工位车辆追踪数据
     * 示例cron：每30秒执行一次 0/30 * * * * ?
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void pullT00TrackData() {
        log.info("===== T00定时任务开始拉取追踪数据 =====");
        // 1. 构造固定入参
        CarPullReqDTO req = new CarPullReqDTO();
        req.setSiteCode("I033");
        req.setVin("LB37822Z6TB037230");
        req.setStationCode("T00");
        req.setRequestQty(5);

        try {
            // 2. HTTP POST请求拉取数据
            TrackApiResp resp = restTemplate.postForObject(TRACK_API_URL, req, TrackApiResp.class);
            if (resp == null || !"success".equals(resp.getCode()) || resp.getData() == null || resp.getData().isEmpty()) {
                log.info("T00接口无有效返回数据");
                return;
            }

            // 3. 转换入库实体，批量插入（根据productSeqNum唯一键去重）
            List<TrackDataItem> dataList = resp.getData();
            LocalDateTime now = LocalDateTime.now();
            for (TrackDataItem item : dataList) {
                ProductTrackInfo track = item.getProductTrackInfo();
                track.setPullTime(now);
                track.setSiteCode("I033");

                // 先查是否已存在，不存在插入；存在更新（唯一键 productSeqNum）
                MesCarInfo exist = mesCarInfoMapper.selectOne(
                        Wrappers.lambdaQuery(MesCarInfo.class)
                                .eq(MesCarInfo::getProductNum, item.getProductTrackInfo().getProductNum()));
                MesCarInfo info = new MesCarInfo();
                BeanUtils.copyProperties(track,info);
                if (exist == null) {
                    mesCarInfoMapper.insert(info);
                    log.debug("新增车辆追踪记录:{}", info.getProductSeqNum());
                } else {
                    mesCarInfoMapper.updateById(info);
                    log.debug("更新车辆追踪记录:{}", info.getProductSeqNum());
                }
            }
            log.info("T00本次拉取完成，共处理{}条记录", dataList.size());
        } catch (Exception e) {
            log.error("T00定时拉取接口异常", e);
            // 可集成重试组件Guava Retryer，失败重试3次
        }
    }
}
