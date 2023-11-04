package com.teenager.content.config;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author Xue
 * @create 2023-04-25-10:42
 */

/**
 * token验证
 */
@Configuration
public class TokenUtils {
    //token秘钥
    private static final String TOKEN_SECRET = "JWT_KEY";

    public static User getUser(String token){
        String username = (String)JWTUtil.parseToken(token).getPayload("username");
//        System.out.println(username);
        return JSON.parseObject(username, User.class);
    }

    @Data
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        //    用于连接其他表，自增
        private Long id;

        private String username;

        private String password;

        //    区分用户 1青少年，2父母，3医生
        private Integer userType;

        private String picturePath;

        private String gender;

        private String address;

        private String email;

        private String introduce;

        private String weiboUsername;
    }
}
