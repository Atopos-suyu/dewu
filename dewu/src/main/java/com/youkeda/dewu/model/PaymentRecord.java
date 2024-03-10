package com.youkeda.dewu.model;

import java.util.Date;

//对支付记录各属性值进行封装
public class PaymentRecord {
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 外部支付渠道主键id
     */
    private String channelPaymentId;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 支付金额
     */
    private Double amount;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 支付状态
     */
    private PaymentStatus payStatus;

    /**
     * 订单额外信息
     */
    private String extendStr;

    /**
     * 支付完成时间
     */
    private String payEndTime;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 获取主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取订单号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取外部支付渠道主键id
     */
    public String getChannelPaymentId() {
        return channelPaymentId;
    }

    /**
     * 设置外部支付渠道主键id
     */
    public void setChannelPaymentId(String channelPaymentId) {
        this.channelPaymentId = channelPaymentId;
    }

    /**
     * 获取渠道类型
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * 设置渠道类型
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     * 获取支付金额
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置支付金额
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取支付类型
     */
    public PayType getPayType() {
        return payType;
    }

    /**
     * 设置支付类型
     */
    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    /**
     * 获取支付状态
     */
    public PaymentStatus getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态
     */
    public void setPayStatus(PaymentStatus payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取订单额外信息
     */
    public String getExtendStr() {
        return extendStr;
    }

    /**
     * 设置订单额外信息
     */
    public void setExtendStr(String extendStr) {
        this.extendStr = extendStr;
    }

    /**
     * 获取支付完成时间
     */
    public String getPayEndTime() {
        return payEndTime;
    }

    /**
     * 设置支付完成时间
     */
    public void setPayEndTime(String payEndTime) {
        this.payEndTime = payEndTime;
    }

    /**
     * 获取创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 设置创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 获取修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
