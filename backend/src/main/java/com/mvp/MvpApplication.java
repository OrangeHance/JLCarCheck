package com.mvp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务
@MapperScan("com.mvp.mapper")
public class MvpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvpApplication.class, args);
    }
}
