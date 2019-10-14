package com.majiang.user.majianguser.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class UserInfo {

    private  String Name;
    private  String Phone;
    private String PassWord;

    @Override
    public String toString() {
        return "UserInfo{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", KeyID='" + KeyID + '\'' +
                ", AddTime=" + AddTime +
                ", ModifTime=" + ModifTime +
                ", IsDelete=" + IsDelete +
                '}';
    }

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

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
