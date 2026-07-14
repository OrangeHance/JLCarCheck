package com.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 车辆信息历史表 mescarinfohis
 */
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
    private String productSeqNum;

    /**
     * 车架号
     */
    private String productNum;

    private String stationCode;

    private String materialCode;

    private String checkItem1;

    private String checkItem2;

    private String checkItem3;

    private String checkItem4;

    private String checkItem5;

}
