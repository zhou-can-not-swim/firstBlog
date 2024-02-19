package com.zqy.demo_java.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqy.demo_java.common.Result;
import com.zqy.demo_java.entity.Blog;
import com.zqy.demo_java.service.BlogService;
import com.zqy.demo_java.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    //    blog分页
    @GetMapping("/blogs/{pageNum}")
    public Result getPages(@PathVariable Integer pageNum){
//        安装创建时间进行排序
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created");

//        通过IPage得到分页信息
        IPage iPage = new Page(pageNum, 5);
        IPage<Blog> page = blogService.page(iPage,queryWrapper);

        return Result.succ(page);
    }

//    blog详情
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        if(blog==null){
            return Result.fail("不存在该博客");
        }

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@RequestBody Blog blog) {

        System.out.println(blog);
        //        Assert.isTrue(false, "公开版不能任意编辑！");

        Blog temp = null;
        //编辑文章
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);

    }


}
