package com.majiang.user.majianguser.exception;

public class majiangRunTimeException extends RuntimeException{
    private Integer code;

    public majiangRunTimeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
