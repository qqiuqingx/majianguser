package com.majiang.user.majianguser.bean;

public class majiangBean extends PrentBean{
    private Integer KeyID;
    private Integer majiangNum;
    private Integer num;

    @Override
    public String toString() {
        return "majiangBean{" +
                "KeyID=" + KeyID +
                ", majiangNum=" + majiangNum +
                ", num=" + num +
                "} " + super.toString();
    }

    public Integer getNum() {
        return num;
    }

    public majiangBean setNum(Integer num) {
        this.num = num;
        return this;
    }

    public Integer getKeyID() {
        return KeyID;
    }

    public majiangBean setKeyID(Integer keyID) {
        KeyID = keyID;
        return this;
    }

    public Integer getMajiangNum() {
        return majiangNum;
    }

    public majiangBean setMajiangNum(Integer majiangNum) {
        this.majiangNum = majiangNum;
        return this;
    }
}
