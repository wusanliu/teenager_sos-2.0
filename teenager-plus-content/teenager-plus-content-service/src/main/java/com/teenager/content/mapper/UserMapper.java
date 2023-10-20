package com.teenager.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teenager.content.model.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xue
 * @create 2023-03-26-9:09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
