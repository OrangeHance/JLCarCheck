package com.mvp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class WareHouseOutDTO {

    private int id;
    private String goodsCode;
    private int outQuantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate  outDate;
    private String remark;
    private LocalDate  createTime;
    private LocalDate updateTime;
    private int total;
    private int pageSize;
    private int pageNum;

}
