package com.majiang.user.majianguser.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderVO implements Serializable {
    private  Integer keyID;
    private String userName;
    private String userPhone;
    private Integer majiangKeyID;
    private Integer Status;
    private String statusName;
    private BigDecimal price;
    private BigDecimal sumPrice;
    private Integer num;
    private String addTime;
    private String   modifyTime;
    private Integer IsDelete;

    @Override
    public String toString() {
        return "orderVO{" +
                "keyID=" + keyID +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", majiangKeyID=" + majiangKeyID +
                ", Status=" + Status +
                ", statusName='" + statusName + '\'' +
                ", price=" + price +
                ", sumPrice=" + sumPrice +
                ", num=" + num +
                ", addTime='" + addTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", IsDelete=" + IsDelete +
                '}';
    }

    public Integer getKeyID() {
        return keyID;
    }

    public void setKeyID(Integer keyID) {
        this.keyID = keyID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getMajiangKeyID() {
        return majiangKeyID;
    }

    public void setMajiangKeyID(Integer majiangKeyID) {
        this.majiangKeyID = majiangKeyID;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
    }
}
