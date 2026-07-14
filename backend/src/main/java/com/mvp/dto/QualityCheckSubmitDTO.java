package com.mvp.dto;

import lombok.Data;
import java.util.List;

@Data
public class QualityCheckSubmitDTO {
    // 条码
    private String barCode;
    // 岗位
    private String jobCode;
    // 车辆信息
    private CarInfoDTO carInfo;
    // 质检项结果列表
    private List<CheckItemDTO> checkList;

    @Data
    public static class CarInfoDTO {
        private Long id;
        private String productSeqNum;
        private String productNum;
        private String stationCode;
        private String materialCode;
        private String model;
        private String series;
        private String config;
        private String selection;
        private String engineType;
    }

    @Data
    public static class CheckItemDTO {
        private Integer id;
        private String desc;
        private String url;
        // 0:NOK 1:OK
        private Integer result;
    }
}
