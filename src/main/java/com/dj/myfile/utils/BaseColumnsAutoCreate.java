package com.dj.myfile.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * 实现数据库通用字段的自动注入（创建时间，创建人，更新时间，更新人）
  *创建人和更新人是根据IP地址获取主机名
 * @Author kcibr
 * @Date 2022/7/21 14:33
 */
@Slf4j
@Component
public class BaseColumnsAutoCreate implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject,"createTime", LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());

        this.strictInsertFill(metaObject,"createUser", String.class, getCurrentUser());
        this.strictInsertFill(metaObject,"updateUser", String.class, getCurrentUser());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"updateUser", String.class,getCurrentUser());
    }
    private String getCurrentUser(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "未知";
    }
}
