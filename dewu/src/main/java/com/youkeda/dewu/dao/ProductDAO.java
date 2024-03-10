package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.dewu.param.BasePageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper  //告诉 MyBatis 框架要为这个接口创建实现类，以便将接口中定义的方法映射到相应的 SQL 操作上
public interface ProductDAO {

    int deleteByPrimaryKey(String id);  //根据产品ID删除对对应的产品记录

    int insert(ProductDO record);  //向数据库中插入一条产品记录

    ProductDO selectByPrimaryKey(String id);  //根据产品ID查询并返回对应的产品记录

    List<ProductDO> selectAll();  //查询并返回所有产品记录的列表

    int updateByPrimaryKey(ProductDO record);  //根据产品ID更新对应的产品记录

    int selectAllCounts();  //查询并返回产品总数的统计结果

    List<ProductDO> pageQuery(BasePageParam param);  //根据分页参数查询并返回符合条件的产品记录列表

}  //这些方法会被实际的数据访问层实现类所实现，并于数据库进行交互
