package com.dj.myfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.myfile.entity.User;
import com.dj.myfile.mapper.UserMapper;
import com.dj.myfile.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper , User> implements UserService {
}
