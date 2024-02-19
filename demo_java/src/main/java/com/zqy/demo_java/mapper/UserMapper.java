package com.zqy.demo_java.mapper;

import com.zqy.demo_java.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
