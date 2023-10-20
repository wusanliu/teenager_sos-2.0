package com.teenager.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.config.TokenUtils;
import com.teenager.content.model.dto.UserDto;
import com.teenager.content.model.po.User;
import com.teenager.content.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @PostMapping("/login")
    public R<UserDto> login(@RequestBody User user, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User user1 = userService.getOne(queryWrapper);

        if (user1==null){
            return R.error("用户不存在");
        }
        if (!user1.getPassword().equals(user.getPassword())){
            return R.error("密码错误");
        }

        String token= TokenUtils.token(user1.getId(),user1.getPassword());
        UserDto userDto = new UserDto();
        userDto.setUser(user1);
        userDto.setToken(token);
        //        把数据存到session域中，后续再使用redis
        session.setAttribute("token",token);
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
        Long userId = TokenUtils.getUserId(token);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper);
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
        Long userId = TokenUtils.getUserId(token);
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
        Long userId = TokenUtils.getUserId(token);
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
