package com.mvp.dto;

import lombok.Data;

@Data
public class CarPullReqDTO {
    private String siteCode;
    private String vin;
    private String stationCode;
    private Integer requestQty;
}
