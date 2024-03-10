package com.youkeda.dewu.dataobject;

import com.youkeda.dewu.model.ProductDetail;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ProductDetailDO {
    /**
     * 主键
     */
    private String id;

    /**
     * 关联商品
     */
    private String productId;

    /**
     * 价格
     */
    private Double price;

    /**
     * 尺码
     */
    private Double size;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 获取主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取关联商品
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置关联商品
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取价格
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置价格
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取尺码
     */
    public Double getSize() {
        return size;
    }

    /**
     * 设置尺码
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * 获取库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
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


    public ProductDetailDO(ProductDetail productDetail) {

        BeanUtils.copyProperties(productDetail, this);  //将ProducDetail对象转换为ProductDetailDO对象

    }

    public ProductDetail convertToModel() {
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(this, productDetail);  //将ProductDetailDO对象转换为ProductDetail对象
        return productDetail;
    }
}
