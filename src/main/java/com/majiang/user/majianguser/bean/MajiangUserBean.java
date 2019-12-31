package com.majiang.user.majianguser.bean;

import com.majiang.user.majianguser.enums.MajiangUserOrderEnum;

public class MajiangUserBean  extends  PrentBean {
    private  Integer keyID;
    private String userPhone;
    private Integer majiangKeyID;
    private Integer Status;
    private String statusName;

    @Override
    public String toString() {
        return "MajiangUserBean{" +
                "keyID=" + keyID +
                ", userPhone='" + userPhone + '\'' +
                ", majiangKeyID=" + majiangKeyID +
                ", Status=" + Status +
                ", statusName='" + statusName + '\'' +
                "} " + super.toString();
    }

    public MajiangUserBean setStatusandName(MajiangUserOrderEnum majiangUserOrderEnum){
        this.Status=majiangUserOrderEnum.getOrderStatus();
        this.statusName=majiangUserOrderEnum.getOrderStatusName();
        return this;
    }

    public Integer getStatus() {
        return Status;
    }

    public MajiangUserBean setStatus(Integer status) {
        Status = status;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public MajiangUserBean setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
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
