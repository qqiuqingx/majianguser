package com.majiang.user.majianguser.bean.vo;

public class UserVO<T> {
    private T t;
    private Integer code;

    @Override
    public String toString() {
        return "UserVO{" +
                "t=" + t +
                ", code=" + code +
                '}';
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
