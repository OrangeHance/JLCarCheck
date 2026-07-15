package com.mvp.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("productSeqNum")
    private String productSeqNum;

    /**
     * 车架号
     */
    @TableField("productNum")
    private String productNum;

    /**
     * 工位编码
     */
    @TableField("stationCode")
    private String stationCode;

    /**
     * 物料编码
     */
    @TableField("materialCode")
    private String materialCode;

    /**
     * 车型
     */
    @TableField("model")
    private String model;

    /**
     * 车系
     */
    @TableField("series")
    private String series;

    /**
     * 配置
     */
    @TableField("config")
    private String config;

    /**
     * 选装配置
     */
    @TableField("selection")
    private String selection;

    /**
     * 发动机型号
     */
    @TableField("engineType")
    private String engineType;
}
