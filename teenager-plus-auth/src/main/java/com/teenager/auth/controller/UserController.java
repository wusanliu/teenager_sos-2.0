package com.teenager.auth.controller;


import cn.hutool.jwt.JWT;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.auth.common.R;
import com.teenager.auth.config.TokenUtils;
import com.teenager.ucenter.model.dto.UserDto;
import com.teenager.ucenter.model.po.User;
import com.teenager.ucenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @author Xue
 * @create 2023-03-26-9:20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public R<UserDto> login(@RequestBody User user){
        //对传入的用户进行判断（封装账号和密码，并且交给AuthenticationManager）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticationManager.authenticate(authenticationToken);

        User user1 = userService.getUserByName(user.getUsername());
        //为了安全在令牌中不放密码
        user1.setPassword(null);
        //将user对象转json
        String userString = JSON.toJSONString(user1);
        //上一步没有抛出异常说明认证成功，我们向用户颁发jwt令牌
        String token = JWT.create()
                .setPayload("username", userString)
                .setKey("JWT_KEY".getBytes(StandardCharsets.UTF_8))
                .sign();
        UserDto userDto = new UserDto();
        userDto.setUser(user);
        userDto.setToken(token);
        return R.success(userDto);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User user1 = userService.getOne(queryWrapper);
        if (user1!=null){
            return R.error("用户已存在");
        }
        userService.save(user);
        return R.success("注册成功");
    }

    @GetMapping("/logout")
    public R<String> logout(HttpSession session){
//        消除session域中的内容
        session.removeAttribute("token");
        return R.success("退出成功");
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public R<User> getUser(HttpServletRequest request){
        String token = request.getHeader("token");
        TokenUtils.User user1 = TokenUtils.getUser(token);
        Long userId = user1.getId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper);
        user.setPassword("    ");
        return R.success(user);
    }

    /**
     * 更新用户信息
     * @param request
     * @param user
     * @return
     */
    @PutMapping("/update")
    public R<String> update(HttpServletRequest request,@RequestBody User user){
        String token = request.getHeader("token");
        TokenUtils.User user1 = TokenUtils.getUser(token);
        Long userId = user1.getId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        user.setId(userId);
        userService.updateById(user);
        return R.success("修改成功");
    }

    /**
     * 更新密码
     * @param request
     * @param newPassword
     * @param oldPassword
     * @return
     */
    @PutMapping("/updatePassword")
    public R<String> updatePassword(HttpServletRequest request,String newPassword,String oldPassword){
        String token = request.getHeader("token");
        TokenUtils.User user1 = TokenUtils.getUser(token);
        Long userId = user1.getId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper);
        if (!user.getPassword().equals(oldPassword)){
            return R.error("原密码错误");
        }
        user.setPassword(newPassword);
        userService.updateById(user);
        return R.success("修改成功");
    }


}
