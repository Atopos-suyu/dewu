package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.ProductDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailDAO {

    List<ProductDetailDO> selectByIds(List<String> ids);  //根据一组产品详情ID查询并返回对应的产品详情记录列表

    int deleteByPrimaryKey(String id);

    int insert(ProductDetailDO record);

    ProductDetailDO selectByPrimaryKey(String id);

    List<ProductDetailDO> selectAll();

    List<ProductDetailDO> selectByProductId(String productId);

    int updateByPrimaryKey(ProductDetailDO record);
}
