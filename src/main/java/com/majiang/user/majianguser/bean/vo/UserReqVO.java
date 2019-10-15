package com.majiang.user.majianguser.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class UserReqVO implements Serializable {
    @ApiModelProperty(value="姓名",required = false)
    public    String Name;
    @ApiModelProperty(value="手机号",required = true,example = "12346798")
    public  String Phone;
    @ApiModelProperty(value="密码",required = true,example = "123456")
    public String PassWord;

  /*  @Override
    public String toString() {
        return "UserReqVO{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", PassWord='" + PassWord + '\'' +
                '}';
    }*/

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
