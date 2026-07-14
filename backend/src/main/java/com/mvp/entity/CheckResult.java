package com.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private String job;

    /**
     * 检查项id
     */
    private String itemId;

    /**
     * ok or nok
     */
    private String isOk;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private String createTime;

    private String update;

    private String updateTime;
}
