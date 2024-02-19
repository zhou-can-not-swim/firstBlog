package com.zqy.demo_java.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqy.demo_java.common.LoginDto;
import com.zqy.demo_java.common.Result;
import com.zqy.demo_java.entity.User;
import com.zqy.demo_java.service.UserService;
import com.zqy.demo_java.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/getuser")
    public Result getUser(@RequestParam("token")String token){
        System.out.println("==========================");
        System.out.println(token);

        try {
            Claims claim = jwtUtils.getClaimByToken(token);
            String id = claim.getSubject();
            User user = userService.getById(Long.valueOf(id));
            System.out.println("user-->"+user);
            return Result.succ(user);
        } catch (NumberFormatException e) {
            return Result.fail("得到user失败");
        }


    }


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginDto.getUsername());
        User user = userService.getOne(queryWrapper);

        if(user==null){
            return Result.fail("没有这个人");
        }

//        对自己的密码加密匹配
        SimpleHash md5Hash1 = new SimpleHash("md5", loginDto.getPassword(), null, 1);
        String pwd = md5Hash1.toHex();

        if(!user.getPassword().equals(pwd)){
            return Result.fail("密码不正确");
        }

//        jwt中存在的密码未加密
        String jwt = jwtUtils.generateToken(user.getId());
        System.out.println("jwt-->"+jwt);

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(user);
    }


    @PostMapping("/regist")
    public Result regist(@RequestBody User user) {

        System.out.println(user);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User temp = userService.getOne(wrapper);
        System.out.println("temp--->" + temp);
        if (temp != null) {
            return Result.fail("存在相同名称");
        } else {
            try {
                temp = new User();
                temp.setUsername(user.getUsername());
                System.out.println(temp);
//            对密码进行加密
                SimpleHash md5Hash1 = new SimpleHash("md5", user.getPassword(), null, 1);
                String pwd = md5Hash1.toHex();
                temp.setPassword(pwd);
                System.out.println(temp);

                temp.setStatus(0);
                System.out.println("temp--->" + temp);

                userService.saveOrUpdate(temp);
                return Result.succ(200, "保存成功", null);
            } catch (Exception e) {
                return Result.fail("再试一次");
            }
        }
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
