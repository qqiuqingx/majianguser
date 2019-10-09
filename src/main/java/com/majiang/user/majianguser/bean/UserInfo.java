package com.majiang.user.majianguser.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

//@ApiModel(description = "用户请求对象")
public class UserInfo {
   // @ApiModelProperty(value="姓名")
    public String Name;
   // @ApiModelProperty(value="手机号")
    public  String Phone;
   // @ApiModelProperty(value="密码")
    public String PassWord;

    @Override
    public String toString() {
        return "UserInfo{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", KeyID='" + getKeyID() + '\'' +
                ", AddTime='" + getAddTime() + '\'' +
                ", ModifTime='" + getModifTime() + '\'' +
                '}';
    }
    @ApiModelProperty(hidden = true)
    public String KeyID;
    @ApiModelProperty(value = "添加时间",hidden = true)
    public Date AddTime;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(hidden = true)
    public Date ModifTime;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(hidden = true)
    public  Integer IsDelete;

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
