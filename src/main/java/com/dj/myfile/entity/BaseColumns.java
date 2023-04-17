package com.dj.myfile.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseColumns {

    /**
     * 创建人，新增时设置，搜索时不搜
     */
    @TableField(fill = FieldFill.INSERT, select = false)
    private String createUser;

    /**
     * 创建时间，新增时设置，搜索时不搜
     */
    @TableField(fill = FieldFill.INSERT, select = false)
    private LocalDateTime createTime;

    /**
     * 更新人，新增和更新时都设置，搜索时不搜
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private String updateUser;

    /**
     * 更新时间,新增和更新时都设置，搜索时不搜
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private LocalDateTime updateTime;
}
