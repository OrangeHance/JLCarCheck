package com.mvp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WareHouseInDTO {

    private int id;
    private String goodsName;
    private String goodsCode;
    private String spec;
    private int quantity;
    private int minQuantity;
    private int maxQuantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate  inDate;
    private String remark;
    private LocalDate  createTime;
    private LocalDate updateTime;
    private int total;
    private int pageSize;
    private int pageNum;

}
