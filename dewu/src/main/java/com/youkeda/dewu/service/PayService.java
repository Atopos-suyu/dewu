package com.youkeda.dewu.service;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.PaymentParam;

import java.util.Map;

public interface PayService {
    /**
     * 支付订单
     *
     * @param paymentParam 支付所需参数
     * @return Result
     */
    Result payOrder(PaymentParam paymentParam);

    /**
     * 支付宝回调接口
     *
     * @param mapParam 支付宝参数
     * @return Result
     */
    Result alipayCallBack(Map<String, String> mapParam);
}
