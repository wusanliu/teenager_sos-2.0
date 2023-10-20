package com.teenager.base.error;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-09-06-17:12
 */
@Data
public class ErrorReturn {
    private String errCode;
    private String errMessage;

    public ErrorReturn(String s, String s1) {
        this.errCode=s;
        this.errMessage=s1;
    }
}
