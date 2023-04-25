package com.dj.myfile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.User;
import com.dj.myfile.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/login")
    public User Login( String account, String password){
        System.out.println("account:"+account+"-----"+"password:"+password);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account)
                .eq("password", password);
//        System.out.println(userService.list(wrapper));
        return userService.getOne(wrapper);
    }
}
