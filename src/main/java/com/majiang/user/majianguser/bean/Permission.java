package com.majiang.user.majianguser.bean;

public class Permission {
    private Integer   permissionNo;
  private Integer  parentId;
  private  String name;
  private String css;
  private String href;
  private Integer type;
  private String permission;
    private Integer  sort;
private Integer IsDelete;

    public Integer getIsDelete() {
        return IsDelete;
    }

    public Permission setIsDelete(Integer isDelete) {
        IsDelete = isDelete;
        return this;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionNo=" + permissionNo +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", css='" + css + '\'' +
                ", href='" + href + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", sort=" + sort +
                '}';
    }

    public Integer getPermissionNo() {
        return permissionNo;
    }

    public Permission setPermissionNo(Integer permissionNo) {
        this.permissionNo = permissionNo;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Permission setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public String getCss() {
        return css;
    }

    public Permission setCss(String css) {
        this.css = css;
        return this;
    }

    public String getHref() {
        return href;
    }

    public Permission setHref(String href) {
        this.href = href;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Permission setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Permission setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Permission setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}
