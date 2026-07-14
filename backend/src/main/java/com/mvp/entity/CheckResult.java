package com.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 检测结果表 checkresult
 */
@Data
@TableName("checkresult")
public class CheckResult {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 岗位
     */
    @TableField("job")
    private String job;

    /**
     * 检查项id
     */
    @TableField("itemId")
    private String itemId;

    /**
     * ok or nok
     */
    @TableField("isOk")
    private String isOk;

    /**
     * 创建人
     */
    @TableField("creater")
    private String creater;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    @TableField("updater")
    private String updater;

    @TableField("updateTime")
    private String updateTime;
    @TableField("hisId")
    private String hisId;
}
