package com.teenager.content.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Xue
 * @create 2023-11-04-15:58
 */
@Data
public class TempMessage {
    private static final long serialVersionUID = 1L;
    private Integer type;
    private String content;
    private Date sendDate;
}
