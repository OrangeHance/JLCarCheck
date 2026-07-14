package com.mvp.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 检测项
 */
@Data
@TableName("carcheckitems")
public class CarCheckItems {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 检查项描述
     */
    private String desc;

    /**
     * 检查项图片
     */
    private String url;

    /**
     * 创建人
     */
    private String create;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String update;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
