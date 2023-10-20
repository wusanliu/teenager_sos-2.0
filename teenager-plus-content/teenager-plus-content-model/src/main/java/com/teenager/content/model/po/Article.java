package com.teenager.content.model.po;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-04-25-10:20
 */
@Data
public class Article {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String content;

    private String picturePath;

}
