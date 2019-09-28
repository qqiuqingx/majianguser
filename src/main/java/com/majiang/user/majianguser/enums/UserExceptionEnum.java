package com.majiang.user.majianguser.enums;

public enum  UserExceptionEnum {
    UserInserException("用户插入异常",1001),
    UserNotException("查无此人",1002),
    UserPhoneNotOnly("用户手机号被占用",1003),
    UserNameNotNull("用户名不能为空",1004),
    UserPassWordNotNull("密码不能为空",1005),
    UserPhoneNotNull("手机号不能为空",1006);


    private String message;
    private Integer code;

    UserExceptionEnum(String message, Integer code) {
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
