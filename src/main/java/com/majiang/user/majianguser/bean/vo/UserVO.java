package com.majiang.user.majianguser.bean.vo;

import com.majiang.user.majianguser.enums.UserEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class UserVO<T> {
    private T date;
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @Override
    public String toString() {
        return "UserVO{" +
                "date=" + date +
                ", code=" + code +
                ", msg=" + msg+
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
