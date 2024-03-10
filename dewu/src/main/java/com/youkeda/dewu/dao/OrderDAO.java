package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.OrderDO;
import com.youkeda.dewu.param.QueryOrderParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDAO {
    
    int insert(OrderDO order);

    int selectCounts(QueryOrderParam param);

    List<OrderDO> pageQuery(QueryOrderParam param);

    OrderDO selectByOrderNumber(String orderNumber);

    int update(OrderDO orderDO);
}
