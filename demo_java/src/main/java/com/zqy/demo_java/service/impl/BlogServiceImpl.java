package com.zqy.demo_java.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqy.demo_java.entity.Blog;
import com.zqy.demo_java.mapper.BlogMapper;
import com.zqy.demo_java.service.BlogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
