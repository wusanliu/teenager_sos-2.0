package com.teenager.content.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * @author Xue
 * @create 2023-05-07-10:06
 */
@Data
public class Questionnaire {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String description;
    private String color;
    private String icon;
    @TableField(exist = false)
    private List<Question> questionList;
}
