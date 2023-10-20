package com.teenager.content.model.po;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-04-25-11:10
 */

/**
 * 心理咨询师
 */
@Data
public class Counselor {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String title;

    private String introduce;

    private String servedNumber;

    private String picturePath;
}
