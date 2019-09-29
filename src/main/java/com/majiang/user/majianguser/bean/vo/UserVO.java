package com.majiang.user.majianguser.bean.vo;

import com.majiang.user.majianguser.enums.UserEnum;

public class UserVO<T> {
    private T date;
    private Integer code;
    private String msg;
    @Override
    public String toString() {
        return "UserVO{" +
                "date=" + date +
                ", code=" + code +
                '}';
    }

    public UserVO(UserEnum userEnum) {
        this.code = userEnum.getCode();
        this.msg = userEnum.getMessage();
    }

    public UserVO(T date, UserEnum userEnum) {
        this.date = date;
        this.code = userEnum.getCode();
        this.msg = userEnum.getMessage();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
