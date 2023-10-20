package com.teenager.content.model.po;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-03-26-9:03
 */
@Data
public class User {

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
