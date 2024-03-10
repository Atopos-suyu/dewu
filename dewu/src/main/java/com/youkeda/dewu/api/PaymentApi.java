package com.youkeda.dewu.api;

import com.youkeda.dewu.model.PaymentRecord;
import com.youkeda.dewu.model.PaymentStatus;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.PaymentParam;
import com.youkeda.dewu.service.PayService;
import com.youkeda.dewu.service.PaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/alipay")
public class PaymentApi {
    private static final Logger logger = LoggerFactory.getLogger(PaymentApi.class);

    @Autowired
    private PayService payService;

    @Autowired
    private PaymentRecordService paymentRecordService;

    @PostMapping(path = "/pay")
    @ResponseBody
    public Result payOrder(@RequestBody PaymentParam paymentParam) {
        Result result = new Result();
        result.setSuccess(true);
        if (StringUtils.isEmpty(paymentParam.getUserId())) {
            result.setSuccess(false);
            result.setMessage("userId is null");
            return result;
        }
        if (paymentParam.getPayAmount() <= 0) {
            result.setMessage("支付金额不能小于0");
            return result;
        }
        PaymentRecord paymentRecord = initPaymentRecord(paymentParam);
        PaymentRecord saveResult = paymentRecordService.save(paymentRecord);
        if (saveResult == null) {
            logger.error("支付流水记录插入失败！");
        }
        return payService.payOrder(paymentParam);
    }  //接受支付参数，验证参数的合法性，保支付记录，调用支付服务进行订单支付操作并返回支付结果

    /**
     * 组装支付记录
     *
     * @param paymentParam paymentParam
     * @return PaymentRecord
     */
    private PaymentRecord initPaymentRecord(PaymentParam paymentParam) {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPayType(paymentParam.getPayType());
        paymentRecord.setPayStatus(PaymentStatus.PENDING);
        paymentRecord.setUserId(paymentParam.getUserId());
        paymentRecord.setOrderNumber(paymentParam.getOrderNumber());
        paymentRecord.setAmount(paymentParam.getPayAmount());
        return paymentRecord;
    }  //根据支付参数创建一个初始的支付记录对象，并返回该对象
}
