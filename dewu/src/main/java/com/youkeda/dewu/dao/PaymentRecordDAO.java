package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.PaymentRecordDO;
import com.youkeda.dewu.param.PaymentRecordQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentRecordDAO {

    int insert(PaymentRecordDO record);  //插入一条支付记录，并返回插入的记录数

    List<PaymentRecordDO> select(PaymentRecordQueryParam paymentRecordQueryParam);  //根据给定的查询参数，从数据库中查询支付记录列表，并返回结果列表

    int update(PaymentRecordDO record);  //更新一条支付记录，并返回更新的记录数
}
