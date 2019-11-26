package com.majiang.user.majianguser.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


public class UserInfo implements Serializable {

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

    public String getName() {
        return Name;
    }

    public UserInfo setName(String name) {
        Name = name;
        return this;
    }

    public String getPhone() {
        return Phone;
    }

    public UserInfo setPhone(String phone) {
        Phone = phone;
        return this;
    }

    public String getPassWord() {
        return PassWord;
    }

    public UserInfo setPassWord(String passWord) {
        PassWord = passWord;
        return this;
    }

    public String getKeyID() {
        return KeyID;
    }

    public UserInfo setKeyID(String keyID) {
        KeyID = keyID;
        return this;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public UserInfo setAddTime(Date addTime) {
        AddTime = addTime;
        return this;
    }

    public Date getModifTime() {
        return ModifTime;
    }

    public UserInfo setModifTime(Date modifTime) {
        ModifTime = modifTime;
        return this;
    }

    public Integer getIsDelete() {
        return IsDelete;
    }

    public UserInfo setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
        return this;
    }
}
