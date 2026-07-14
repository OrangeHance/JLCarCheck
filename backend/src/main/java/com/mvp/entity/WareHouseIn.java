package com.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("warehouse_in")
public class WareHouseIn {

    @TableId(type = IdType.AUTO)
    private int id;
    private String goods_name;
    private String goods_code;
    private String spec;
    private String quantity;
    private LocalDateTime in_date;
    private String remark;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
