package com.majiang.user.majianguser.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PrentBean {
    private String KeyID;
    private Date AddTime;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ModifTime;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private  Integer IsDelete;

    public Integer getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
    }

    public String getKeyID() {
        return KeyID;
    }

    public void setKeyID(String keyID) {
        KeyID = keyID;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }

    public Date getModifTime() {
        return ModifTime;
    }

    public void setModifTime(Date modifTime) {
        ModifTime = modifTime;
    }
}
