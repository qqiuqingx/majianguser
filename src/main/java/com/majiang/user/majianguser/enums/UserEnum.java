package com.majiang.user.majianguser.enums;

public enum UserEnum {
    SUCSS("成功",1000),
    UserNameNotNull("用户名不能为空",1004),
    UserPassWordNotNull("密码不能为空",1005),
    UserPhoneNotNull("手机号不能为空",1006);
    private String message;
    private Integer code;

    UserEnum(String message, Integer code) {
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
