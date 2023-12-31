package com.teenager.content.model.dto;

import com.teenager.content.model.po.User;
import lombok.Data;

/**
 * @author Xue
 * @create 2023-04-25-10:35
 */
@Data
public class UserDto {
    private User user;
    private String token;
}
