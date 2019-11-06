package com.majiang.user.majianguser.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
//@ApiModel
public class PrentBean implements Serializable {
    private Date addTime;
    private Date   modifyTime;
    private Integer IsDelete;

    @Override
    public String toString() {
        return "PrentBean{" +
                "addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", IsDelete=" + IsDelete +
                '}';
    }

    public Date getAddTime() {
        return addTime;
    }

    public PrentBean setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public PrentBean setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Integer getIsDelete() {
        return IsDelete;
    }

    public PrentBean setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
        return this;
    }
}
