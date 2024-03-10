package com.youkeda.dewu.param;

import com.youkeda.dewu.model.PayType;
import com.youkeda.dewu.model.PaymentStatus;

//方便地封装支付记录的查询参数
public class PaymentRecordQueryParam {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 支付状态
     */
    private PaymentStatus paymentStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
