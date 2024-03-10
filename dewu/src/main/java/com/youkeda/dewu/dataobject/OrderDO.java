package com.youkeda.dewu.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.OrderStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;


public class OrderDO {
    /**
     * 主键id
     */

    private String id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String productDetailId;

    /**
     * 订单总价格
     */
    private Double totalPrice;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId;
    }

    //构造函数将Order对象转换为OrderDO对象
    public OrderDO(Order order) {
        BeanUtils.copyProperties(order, this);  //将Order对象的属性复制给OrderDO对象
        if (order.getStatus() != null) {
            this.setStatus(order.getStatus().toString());  //将order对象的状态转换为字符串并设置给当前对象this的状态属性
        }

    }

    public Order convertToModel() {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        if (!StringUtils.isEmpty(this.getStatus())) {
            order.setStatus(OrderStatus.valueOf(this.getStatus()));  //将状态属性值从字符串类型转换为枚举类型并设置给order对象的状态属性
        }

        return order;
    }
}
