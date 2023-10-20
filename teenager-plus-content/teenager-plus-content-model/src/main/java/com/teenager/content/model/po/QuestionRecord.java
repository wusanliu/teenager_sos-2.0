package com.teenager.content.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Xue
 * @create 2023-05-07-11:49
 */
@Data
public class QuestionRecord {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Date testTime;
    private String result;

}
