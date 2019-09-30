package com.majiang.user.majianguser.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户请求对象")
public class UserInfo extends PrentBean{
    @ApiModelProperty(value="姓名",name = "name")
    private String Name;
    @ApiModelProperty(value="手机号",name="phone")
    private  String Phone;
    @ApiModelProperty(value="密码",name = "password")
    private String PassWord;

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
