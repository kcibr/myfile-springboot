package com.dj.myfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.myfile.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
