package com.majiang.user.majianguser.bean;

import java.util.Date;

public class Role {
      private  Integer roleNo;
      private  String  name;
      private Date addTime;
    private Date   modifyTime;
    private Integer IsDelete;

    public Integer getIsDelete() {
        return IsDelete;
    }

    public Role setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
        return this;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleNo=" + roleNo +
                ", name='" + name + '\'' +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", IsDelete=" + IsDelete +
                '}';
    }

    public Integer getRoleNo() {
        return roleNo;
    }

    public Role setRoleNo(Integer roleNo) {
        this.roleNo = roleNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Role setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public Role setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
}
