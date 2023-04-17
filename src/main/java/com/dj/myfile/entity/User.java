package com.dj.myfile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
//@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class User {
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 账号
     */
    private String account;

    private String password;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 用户组
     */
    private String fileGroup;
    /**
     * 用户状态
     */
    private String state;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 性别
     */
    private String gender;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 地址
     */
    private String address;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后登录ip
     */
    private String lastLoginIp;
    /**
     * 注册时间
     */
    private LocalDateTime regDate;
    /**
     * 注册ip
     */
    private String regIp;

    /**
     * 逻辑删除
     */
    private Integer isDelete;
}
