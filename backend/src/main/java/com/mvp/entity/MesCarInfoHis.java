package com.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 车辆信息历史表 mescarinfohis
 */
@Builder
@Data
@TableName("mescarinfohis")
public class MesCarInfoHis {

    /**
     * 主键自增ID
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

    @TableField("stationCode")
    private String stationCode;
    @TableField("materialCode")
    private String materialCode;
    @TableField("job")
    private String job;

    @TableField("creater")
    private String creater;

    @TableField("createTime")
    private String createTime;

}
