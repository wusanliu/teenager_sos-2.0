package com.teenager.content.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * @author Xue
 * @create 2023-05-07-9:57
 */
@Data
public class Question {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long questionnaireId;

    private int type;

    private String label;

    private Boolean required;

    private int value;

    @TableField(exist = false)
    private List<Option> options;
}
