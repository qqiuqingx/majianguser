package com.majiang.user.majianguser.bean;

public class UserInfo extends PrentBean{
    private String Name;
    private  String Phone;
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
