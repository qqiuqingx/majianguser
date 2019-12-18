package com.majiang.user.majianguser.bean;

public class MajiangUserBean  extends  PrentBean {
    private  Integer keyID;
    private String userPhone;
    private Integer majiangKeyID;

    @Override
    public String toString() {
        return "MajiangUserBean{" +
                "keyID=" + keyID +
                ", userPhone='" + userPhone + '\'' +
                ", majiangKeyID=" + majiangKeyID +
                "} " + super.toString();
    }

    public Integer getKeyID() {
        return keyID;
    }

    public MajiangUserBean setKeyID(Integer keyID) {
        this.keyID = keyID;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public MajiangUserBean setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public Integer getMajiangKeyID() {
        return majiangKeyID;
    }

    public MajiangUserBean setMajiangKeyID(Integer majiangKeyID) {
        this.majiangKeyID = majiangKeyID;
        return this;
    }
}
