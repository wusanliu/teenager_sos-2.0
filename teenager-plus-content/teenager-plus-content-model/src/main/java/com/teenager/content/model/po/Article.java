package com.teenager.content.model.po;

import lombok.Data;

import java.util.Date;

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

    private String avator;

    private String tag;

    private Integer thumb;

    private Integer comment;

    private Integer view;

    private Date publishDate;
}
