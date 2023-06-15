package com.dj.myfile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.User;
import com.dj.myfile.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    @GetMapping("/login")
    public User Login( String account, String password){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account)
                .eq("password", password);
        return userService.getOne(wrapper);
    }
    @GetMapping("/register")
    public String Register(String account , String password){
        System.out.println("注册请求");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account)
                .eq("password", password);
        if(userService.getOne(wrapper) == null){
            User user = new User();
            Random r = new Random();
            user.setAccount(account);
            user.setFileGroup(account);
            user.setUserType("普通用户");
            user.setNickname("用户"+ r.nextInt(10000000));
            user.setState("在线");
            user.setIsDelete(0);
            userService.save(user);
        }
        return "请求成功";
    }
}
