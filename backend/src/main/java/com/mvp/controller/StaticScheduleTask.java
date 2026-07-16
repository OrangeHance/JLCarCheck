package com.mvp.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StaticScheduleTask {

    // 每10秒执行一次
    @Scheduled(fixedRate = 10000)
    public void task1() {
        System.out.println("固定频率定时任务执行：" + System.currentTimeMillis());
    }
}
