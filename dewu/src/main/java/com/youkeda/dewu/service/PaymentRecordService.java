package com.youkeda.dewu.service;

import com.youkeda.dewu.model.PaymentRecord;
import com.youkeda.dewu.param.PaymentRecordQueryParam;

import java.util.List;

/**
 * @Author songchuanming
 * @DATE 2020/7/7.
 */
public interface PaymentRecordService {
    /**
     * 添加或修改支付记录
     *
     * @return PaymentRecord
     */
    PaymentRecord save(PaymentRecord paymentRecord);

    /**
     * 查询支付记录
     *
     * @param queryParam 查询参数
     * @return List<PaymentRecord>
     */
    List<PaymentRecord> query(PaymentRecordQueryParam queryParam);

    /**
     * 更新状态
     *
     * @param paymentRecord 支付记录
     * @return PaymentRecord
     */
    PaymentRecord updateStatus(PaymentRecord paymentRecord);

}
