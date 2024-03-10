package com.youkeda.dewu.model;

//定义一个枚举类型 OrderStatus，表示订单的状态。枚举类型是一种特殊的类，用于定义一组固定的常量
public enum  OrderStatus {
    /**
     * 待付款
     */
    WAIT_BUYER_PAY,

    /**
     * 订单关闭
     */
    TRADE_CLOSED,

    /**
     * 订单已支付成功
     */
    TRADE_PAID_SUCCESS,

    /**
     * 订单支付失败
     */
    TRADE_PAID_FAILED,
}
