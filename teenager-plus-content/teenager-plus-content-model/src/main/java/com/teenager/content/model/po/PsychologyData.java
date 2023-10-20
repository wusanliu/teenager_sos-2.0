package com.teenager.content.model.po;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-05-07-8:53
 */
@Data
public class PsychologyData {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String weiboUsername;

    private String result;

    private String suggestion;

    private Boolean isShowed;
}
