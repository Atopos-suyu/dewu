package com.youkeda.dewu.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youkeda.dewu.model.Paging;

import java.util.Date;

/**
 * @Author jiaoheng
 * @DATE 2020-07-13
 */
 
 //用于表示查询订单的参数  继承自Paging类表示该类还具有分页相关的属性和行为，可以实现对查询结果进行分页展示
public class QueryOrderParam extends Paging {

    /**
     * 商品Id
     */
    private String productDetailId;
    /**
     * 查询近几天的购买记录
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
