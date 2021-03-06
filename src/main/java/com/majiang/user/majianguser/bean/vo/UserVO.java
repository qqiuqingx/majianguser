package com.majiang.user.majianguser.bean.vo;

import com.majiang.user.majianguser.enums.UserEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class UserVO<T> {

    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    private Long count;
    private T date;


    public Long getCount() {
        return count;
    }

    public UserVO<T> setCount(Long count) {
        this.count = count;
        return this;
    }

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

    public T getDate() {
        return date;
    }

    public UserVO<T> setDate(T date) {
        this.date = date;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public UserVO<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public UserVO<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
