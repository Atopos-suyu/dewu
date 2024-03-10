package com.youkeda.dewu.dataobject;

import com.youkeda.dewu.model.Product;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ProductDO {
    /**
     * 主键
     */
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品默认展示的价格
     */
    private Double price;

    /**
     * 商品销量
     */
    private Integer purchaseNum;

    /**
     * 商品介绍
     */
    private String productIntro;

    /**
     * 商品图片
     */
    private String productImgs;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 创建日期
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
     * 获取商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品默认展示的价格
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置商品默认展示的价格
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取商品销量
     */
    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    /**
     * 设置商品销量
     */
    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    /**
     * 获取商品介绍
     */
    public String getProductIntro() {
        return productIntro;
    }

    /**
     * 设置商品介绍
     */
    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public String getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(String productImgs) {
        this.productImgs = productImgs;
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
     * 获取创建日期
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置创建日期
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }


    public ProductDO(Product product) {

        BeanUtils.copyProperties(product, this);  //实现了将Product对象转换为ProductDO对象的功能

    }

    public Product convertToModel() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product);  //将ProductDO对象转换为Product
        return product;
    }
}
