package com.majiang.user.majianguser.bean;

import com.majiang.user.majianguser.enums.MajiangUserOrderEnum;

import java.math.BigDecimal;

public class MajiangUserBean  extends  PrentBean {
    private  Integer keyID;
    private String userName;
    private String userPhone;
    private Integer majiangKeyID;
    private Integer Status;
    private String statusName;
    private BigDecimal price;
    private BigDecimal sumPrice;
    private Integer num;

    @Override
    public String toString() {
        return "MajiangUserBean{" +
                "keyID=" + keyID +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", majiangKeyID=" + majiangKeyID +
                ", Status=" + Status +
                ", statusName='" + statusName + '\'' +
                ", price=" + price +
                ", sumPrice=" + sumPrice +
                ", num=" + num +
                "} " + super.toString();
    }

    public String getUserName() {
        return userName;
    }

    public MajiangUserBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MajiangUserBean setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public MajiangUserBean setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public MajiangUserBean setNum(Integer num) {
        this.num = num;
        return this;
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
