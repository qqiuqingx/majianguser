package com.majiang.user.majianguser.enums;

public enum UserEnum {
    SUCSS("成功",0),
    //注册相关
    UserNameNotNull("用户名不能为空",1004),
    UserPassWordNotNull("密码不能为空",1005),
    UserPhoneNotNull("手机号不能为空",1006),
    //登录相关
    PhoneNotRegistered("该手机号未注册",1007),
    PassWordNotright("用户名或密码错误",1008),
    //用户查询相关
    NoUser("没有用户",2009),
    PhoneNotre("请输入正确的手机号",3001),
    //统一错误
    application("系统错误",9999);
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
