package com.teenager.content.model.po;

import lombok.Data;

/**
 * @author Xue
 * @create 2023-05-07-9:55
 */
@Data
public class Option {
    private int label;
    private String value;

    public Option(){
        super();
    }

    public Option(int label,String value){
        this.label=label;
        this.value=value;
    }
}
