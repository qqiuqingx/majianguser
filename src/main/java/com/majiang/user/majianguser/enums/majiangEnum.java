package com.majiang.user.majianguser.enums;

public enum majiangEnum {

     MAJIANGNUM("座位已满,请重新选座",10001)
    ,LOGINFORNOW("请先登录",10002);
    private String message;
    private Integer code;

    majiangEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
