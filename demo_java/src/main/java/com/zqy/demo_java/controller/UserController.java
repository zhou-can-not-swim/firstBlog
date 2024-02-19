package com.zqy.demo_java.controller;

import com.zqy.demo_java.entity.User;
import com.zqy.demo_java.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/index")
    public User index(){
        return userService.getById(1L);
    }


}
