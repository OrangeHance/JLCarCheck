package com.mvp.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 车辆基础信息表 sys.mescarinfo
 */
@Data
@TableName("mescarinfo")
public class MesCarInfo {

    /**
     * 自增主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品序列码
     */
    private String productSeqNum;

    /**
     * 车架号
     */
    private String productNum;

    /**
     * 工位编码
     */
    private String stationCode;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 车型
     */
    private String model;

    /**
     * 车系
     */
    private String series;

    /**
     * 配置
     */
    private String config;

    /**
     * 选装配置
     */
    private String selection;

    /**
     * 发动机型号
     */
    private String engineType;
}
