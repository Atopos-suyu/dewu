package com.youkeda.dewu.service;

import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.param.BasePageParam;

import java.util.List;

public interface ProductService {

    /**
     * 增加或修改商品
     *
     * @param product 商品
     * @return Product
     */
    int save(Product product);

    /**
     * 分页查询商品
     *
     * @param param 商品分页参数
     * @return PagingData<Product>
     */
    Paging<Product> pageQueryProduct(BasePageParam param);

    /**
     * 根据主键id获取商品信息
     *
     * @param id 主键id
     * @return Product
     */
    Product get(String id);
}
