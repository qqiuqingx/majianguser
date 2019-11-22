package com.majiang.user.majianguser.bean.vo;

import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class MajiangVo<T> {

    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    private Long count;
    private T date;

    public MajiangVo(majiangEnum majiangEnum, Long count, T date) {
        this.code = majiangEnum.getCode();
        this.msg = majiangEnum.getMessage();
        this.count = count;
        this.date = date;
    }
    public MajiangVo(majiangEnum majiangEnum) {
        this.code = majiangEnum.getCode();
        this.msg = majiangEnum.getMessage();
    }

    public MajiangVo(UserEnum userEnum, Long count, T date) {
        this.code = userEnum.getCode();
        this.msg = userEnum.getMessage();
        this.count = count;
        this.date = date;
    }

    public MajiangVo(UserEnum userEnum) {
        this.code = userEnum.getCode();
        this.msg = userEnum.getMessage();
    }

    @Override
    public String toString() {
        return "MajiangVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", date=" + date +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public MajiangVo<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MajiangVo<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public MajiangVo<T> setCount(Long count) {
        this.count = count;
        return this;
    }

    public T getDate() {
        return date;
    }

    public MajiangVo<T> setDate(T date) {
        this.date = date;
        return this;
    }
}
