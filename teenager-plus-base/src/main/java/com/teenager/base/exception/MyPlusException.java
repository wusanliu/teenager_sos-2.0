package com.teenager.base.exception;

/**
 * @version 1.0
 * @description 本项目自定义异常类型
 */
public class MyPlusException extends RuntimeException {

    private String errMessage;

    public MyPlusException() {
    }

    public MyPlusException(String message) {
        super(message);
        this.errMessage = message;

    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public static void cast(String message){
        throw new MyPlusException(message);
    }
    public static void cast(CommonError error){
        throw new MyPlusException(error.getErrMessage());
    }

}
