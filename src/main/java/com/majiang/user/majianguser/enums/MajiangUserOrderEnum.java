package com.majiang.user.majianguser.enums;

public enum MajiangUserOrderEnum {
    Order_PAY(1,"已支付"),
    ORDER_STAY_PAY(0,"待支付"),
    ORDER_CANCEL(2,"已取消"),
    ORDER_REFUND(3,"已退款")
    ;

    private Integer OrderStatus;
    private String OrderStatusName;

    MajiangUserOrderEnum(Integer orderStatus, String orderStatusName) {
        OrderStatus = orderStatus;
        OrderStatusName = orderStatusName;
    }

    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public MajiangUserOrderEnum setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
        return this;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public MajiangUserOrderEnum setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
        return this;
    }
}
