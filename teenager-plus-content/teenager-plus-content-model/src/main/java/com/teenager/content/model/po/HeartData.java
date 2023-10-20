package com.teenager.content.model.po;

/**
 * @author Xue
 * @create 2022-11-28-10:31
 */
public class HeartData {
    private Integer heart;
    private boolean flag;

    public HeartData() {
    }

    public HeartData(Integer heart, boolean flag) {
        this.heart = heart;
        this.flag = flag;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
