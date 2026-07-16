package com.mvp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductTrackInfo {
    private String productSeqNum;    // T002602270093 唯一业务主键
    private String productNum;       // VIN码
    private String stationCode;
    private String materialCode;
    private String model;
    private String series;
    private String config;
    private String selection;
    private String engineType;

    // 入库辅助字段
    private LocalDateTime pullTime;  // 拉取时间
    private String siteCode;
}
