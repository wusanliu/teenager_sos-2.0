package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.UserMapper;
import com.teenager.content.model.po.User;
import com.teenager.content.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-03-26-9:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
