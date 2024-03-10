package com.youkeda.dewu.param;

import com.youkeda.dewu.model.PayType;

//param包主要是前端向后端的自定义对象
public class PaymentParam {
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 总金额，单位分
     */
    private double totalAmount;

    /**
     * 待支付金额
     */
    private double payAmount;

    /**
     * 支付类型
     */
    private PayType payType = PayType.ALIPAY;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }
}
