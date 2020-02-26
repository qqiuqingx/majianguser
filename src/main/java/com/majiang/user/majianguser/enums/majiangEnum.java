package com.majiang.user.majianguser.enums;

public enum majiangEnum {

     MAJIANGNUM("座位已满,请重新选座",10001)
    ,REPEAT("请勿重复预定",10003)
    ,DEFEATED("订桌失败",10004)
    ,LOGINFORNOW("请先登录",10002)
    ,NO_ORDER("没有订单",10005)
    ,IS_EMPY("未传入具体的数据",10006);
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
