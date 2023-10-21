package com.teenager.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teenager.ucenter.model.po.User;

/**
 * @author Xue
 * @create 2023-03-26-9:12
 */
public interface UserService extends IService<User> {
    User getUserByName(String username);
}
