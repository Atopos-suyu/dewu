package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.PaymentRecordDAO;
import com.youkeda.dewu.dataobject.PaymentRecordDO;
import com.youkeda.dewu.model.PaymentRecord;
import com.youkeda.dewu.param.PaymentRecordQueryParam;
import com.youkeda.dewu.service.PaymentRecordService;
import com.youkeda.dewu.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {
    @Autowired
    private PaymentRecordDAO paymentRecordDAO;

    @Override
    public PaymentRecord save(PaymentRecord paymentRecord) {
        if (paymentRecord == null) {
            return null;
        }
        //如果ID为空，表示这是一条新的支付记录
        if (StringUtils.isEmpty(paymentRecord.getId())) {
            PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);  //代码会将PaymentRecord对象转换为PaymentRecordDO对象
            paymentRecordDO.setId(UUIDUtils.getUUID());  //生成唯一的ID
            int insertSize = paymentRecordDAO.insert(paymentRecordDO);  //将该记录插入到数据库中
            if (insertSize < 1) {
                return null;
            }
            paymentRecord.setId(paymentRecordDO.getId());  //插入成功则将数据库生成的ID设置回paymentRecord对象
            return paymentRecord;
        } else {
            PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);  
            int updateSize = paymentRecordDAO.update(paymentRecordDO);  //否则已存在支付记录，更新数据库中对应的记录
            if (updateSize < 1) {
                return null;
            }
            return paymentRecord;
        }
    }
 
    @Override
    public List<PaymentRecord> query(PaymentRecordQueryParam queryParam) {
        List<PaymentRecord> paymentRecords = new ArrayList<>();
        List<PaymentRecordDO> paymentRecordDOS = paymentRecordDAO.select(queryParam);
        if (CollectionUtils.isEmpty(paymentRecordDOS)) {
            return paymentRecords;
        }
        paymentRecordDOS.forEach(paymentRecordDO -> {
            PaymentRecord paymentRecord = paymentRecordDO.convertToModel();
            paymentRecords.add(paymentRecord);
        });
        return paymentRecords;
    }

    @Override
    public PaymentRecord updateStatus(PaymentRecord paymentRecord) {
        PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);
        int updateSize = paymentRecordDAO.update(paymentRecordDO);
        if (updateSize < 1) {
            return null;
        }
        return paymentRecord;
    }
}
