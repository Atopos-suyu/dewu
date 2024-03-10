package com.youkeda.dewu.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.dewu.model.*;
import com.youkeda.dewu.param.PaymentParam;
import com.youkeda.dewu.param.PaymentRecordQueryParam;
import com.youkeda.dewu.service.OrderService;
import com.youkeda.dewu.service.PayService;
import com.youkeda.dewu.service.PaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentRecordService paymentRecordService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${alipay.app.id:2021001172660270}")
    private String aliPayAppId;

    @Value("${alipay.app.privatekey:MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnv9kfis56VG2XVZxAq5S10aEumZgV3VvoOD5VdpMrd9wfRuRRI7fMsDB0HXo5uHkJZ+SXzaJh1CQl5R6HrSX64EZy9/5sHyFRKabPY4Nxrrzi++tS4C/SFYYT6TYEKAzMk7wWBP0NvFhbNfVpne8dE6K98NYLkz5kAXXHHQWyPQqxTDN/Iq6+0ZJa6o+7JXYO3CEC8+qEvYCo478G9PA0ooWvEVargdHPavXBwHH0TfXyxwS3prG2LUmhf+a9qUxbtN1BKsujtm+ff//S5RY1TDwwhR0GnLUGZKlf+WeYJjIBAHUX+zEPOm9FRixdIeHgnmF3cAuGb+R9jAenxVTBAgMBAAECggEAO0p398ocCOjmg2LjA4ih21Ho4ouvUasX3RBkF9j9U5Pd3cA02ukBAfwUZDY3CUfGoCh0h6NLDcDptesxy0rL7cxvmhtFdfna0NEkAJFv2DKm2KOqHXTX8i1hYpA/Y2C0hWqCRFYnCz/TCwobX+VOqrxR/UiunxDAMKDDfEkpxkFw8A8tiQBay+ZlGmUpydc4Hl0/26lpKnxPWypfhIFVB8zEwWnl8OGMosPCeMa0ByXloDbKo4GMMGTpw9HvatbhCSZPWmLIcytZI61e/7ziGsIP2sAfsnHhNg7W88AAGzvZV98S305+98+6ZUtk1Fv6gwQBMTwn0UiFuqc0ALd+sQKBgQDwpxYO/R4Bw/4gUj9//5pcycgCRo7Gr2TO7fPq9Fq5uAIMrnpi0e+AX4dY+sNEoCTAqwRGpdvcXBsj/AWsenYqiKwWfqxIWQNvZnf/xxsJe1TLk4BYmqAYXevmHGV1/SoUXgsfiEyPKu43VTCanXc/l46dvUFAkwBUHv/ttuiUowKBgQCycomhkPBubLumuCSgm79q5tv/VtayiH/L81NDcPlUDGkqBnvnwDPgTyCxMd4L+gCphSiyBRpdQ/SX3jKfFsp/JN4n37Dr0ZNYeENXCEEIh+D0QFyqwO8tTXMuevryUjisHgZSy004Y7chNFHBUVFwCJ/2xaldqA9WVdQKSuqjSwKBgGONS0PCE/K5CFyIibpCm7G8y1+dnpy0m+g6aYgNs6ZWZ4qldv2ASSp62jvF6JdwBCQr2uX64Nvkwll9fT9fnZh013OqzUxUfmZMJmIKFLY0bdyVVSfSN149JEQaBSLtKsYoLUPLF5i2MrtzI1sivtzwrk+0pdS3uxARjt/gpZAvAoGBALBEM29EnDp3bWThwExljE8se2Ndg/YWnyX21OhpT9+V4suAXCQv1w5bGw/tEkkCSmUpA2nVYJV/6ruY4KgE+0FcSZVZgIlwGvvoz5vIq3Shw7OBYAfLTHaTapMfJ4L1dMWPYu+lokFxPhOuepNan/bqjhhUZ1f8Cipd3XXNSrjBAoGAQ2pc7xh1jxCTTtN+EmxQRGjwid0AcDEZAn4m00mCFkRhvVfa4Df5GcAgNajTBsttdi/DXtu216whQ7ceYKAPsKzEO4w7R7DoMzhnL9NqNtL9uogwwYO72uPEVn4c+hQS/qFRmSsfIkCrT2J5sGuXU7+4gUO8V8Ox+RIOt6kkG6Y=}")
    private String aliPayAppPrivateKey;

    @Value("${alipay.publickey:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkJAtCWlSvkZ05srfAmvOt/XU701GmpF3aO7XozmmZbzjTOUAcc8BzrAsqIeXJVOPRJz75fCVZ6rcsx4P2PWGHCoB293RPJpBnDT1VBVMq7k8Hw9VOJRuq56L0PZxtVHYjUA8i4vUmXc8j5K4rLGp+PC9VqNVJpj8Njv2R3ZeLndAd0B1//73SfKRSRZMNoPAl/XPSY7MAfGLzNm3B3FPVbJIEt9TM+/ijtlLpzTFCDLaLvy8EFsvoZwgpVkbxT9iRFvFWomz29/oH4xkSsZFaTMeswPUyERoMXhqMmW8hmVT/yBjiEx/NNC32Bu0Ic4DD01JZXDr/jDDjh1IA2uYNQIDAQAB}")
    private String aliPayPublicKey;

    public Result aliPay(String orderId, PaymentParam paymentParam) {
        Result result = new Result();
        result.setSuccess(true);

        //根据订单id查询出订单信息
        // Order order = orderService.getByOrderNumber(orderId);
        // if (order == null) {
        //     result.setSuccess(false);
        //     result.setMessage("未查询到任何订单信息");
        //     return result;
        // }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", this.aliPayAppId,
                                                            this.aliPayAppPrivateKey, "json", "UTF-8",
                                                            this.aliPayPublicKey, "RSA2");
        //创建API对应的request
        AlipayTradeWapPayRequest request = getAlipayTradeWapPayRequest(orderId, paymentParam);

        AlipayTradeWapPayResponse response = null;
        try {
            //调用SDK生成表单
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            logger.error("e is:", e);
        }
        if (response.isSuccess()) {
            String channelId = response.getTradeNo();
            //更新支付记录
            updatePayRecord(null, channelId, PayType.ALIPAY.toString(), paymentParam.getOrderNumber(),
                            PaymentStatus.PENDING);
        }
        return result;
    }

    @Override
    public Result alipayCallBack(Map<String, String> mapParam) {
        Result result = new Result();
        result.setSuccess(true);
        String status = mapParam.get("trade_status");
        String orderNum = mapParam.get("out_trade_no");
        Order order = orderService.getByOrderNumber(orderNum);  //根据订单号获取订单信息
        String endTime = mapParam.get("gmt_close");
        if (order != null) {
            //交易成功
            if ("TRADE_SUCCESS".equals(status)) {
                // 更新订单状态
                orderService.updateOrderStatus(orderNum, OrderStatus.TRADE_PAID_SUCCESS);

                //更新支付流水
                PaymentRecordQueryParam queryParam = new PaymentRecordQueryParam();
                queryParam.setOrderNumber(orderNum);
                List<PaymentRecord> paymentRecords = paymentRecordService.query(queryParam);
                if (!CollectionUtils.isEmpty(paymentRecords)) {
                    PaymentRecord paymentRecord = paymentRecords.get(0);
                    paymentRecord.setPayStatus(PaymentStatus.SUCCESS);
                    //更新支付流水状态
                    paymentRecordService.updateStatus(paymentRecord);
                }
            }

            //交易关闭
            if ("TRADE_CLOSED".equals(status)) {
                // 更新订单状态
                orderService.updateOrderStatus(orderNum, OrderStatus.TRADE_PAID_FAILED);

                //更新支付流水
                PaymentRecordQueryParam queryParam = new PaymentRecordQueryParam();
                queryParam.setOrderNumber(orderNum);
                List<PaymentRecord> paymentRecords = paymentRecordService.query(queryParam);
                if (!CollectionUtils.isEmpty(paymentRecords)) {
                    PaymentRecord paymentRecord = paymentRecords.get(0);
                    paymentRecord.setPayStatus(PaymentStatus.FAILURE);
                    //更新支付流水状态
                    paymentRecordService.updateStatus(paymentRecord);
                }
            }
        }
        return result;
    }

    /**
     * 组装支付宝支付参数
     *
     * @param orderId      订单号
     * @param paymentParam 支付参数
     * @return AlipayTradeWapPayRequest
     */
     //用于生成支付宝手机网站支付请求对象
    private AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(String orderId, PaymentParam paymentParam) {
        Map<String, Object> bizContentMap = new HashMap<>();  //创建一个空的bizContentMap用于存储支付请求的具体参数
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setApiVersion("1.0");
        request.setReturnUrl("");
        request.setNotifyUrl("");
        bizContentMap.put("out_trade_no", orderId);
        bizContentMap.put("total_amount", paymentParam.getPayAmount());
        bizContentMap.put("quit_url", "www.youkeda.com");
        bizContentMap.put("product_code", "QUICK_WAP_WAY");

        try {
            request.setBizContent(objectMapper.writeValueAsString(bizContentMap));  //将bizContentMap转换为JSON字符串并设置为请求对象的业务参数内容
        } catch (JsonProcessingException e) {
            logger.error("e is:", e);
        }
        return request;
    }

    @Override
    public Result payOrder(PaymentParam paymentParam) {
        Result result = new Result<>();
        switch (paymentParam.getPayType()) {
            case ALIPAY:
                result = this.aliPay(paymentParam.getOrderNumber(), paymentParam);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 更新支付记录信息
     *
     * @param endTime       支付结束时间
     * @param orderNum      订单号
     * @param channelId     渠道id
     * @param channelType   渠道类型
     * @param paymentStatus 支付状态
     */
    private void updatePayRecord(String endTime, String channelId, String channelType, String orderNum,
                                 PaymentStatus paymentStatus) {
        PaymentRecordQueryParam param = new PaymentRecordQueryParam();
        param.setOrderNumber(orderNum);  //创建对象并设置了订单号作为查询条件
        List<PaymentRecord> paymentList = paymentRecordService.query(param);  //查询符合条件的支付记录列表
        if (!CollectionUtils.isEmpty(paymentList)) {
            PaymentRecord paymentRecord = paymentList.get(0);
            paymentRecord.setPayStatus(paymentStatus);
            if (!StringUtils.isEmpty(endTime)) {
                paymentRecord.setPayEndTime(endTime);
            }
            if (!StringUtils.isEmpty(channelId)) {
                paymentRecord.setChannelPaymentId(channelId);
            }
            if (!StringUtils.isEmpty(channelType)) {
                paymentRecord.setChannelType(channelType);
            }
            PaymentRecord paymentRecordResult = paymentRecordService.save(paymentRecord);  //保存更新后的支付记录信息
            if (paymentRecordResult == null) {
                logger.error("更新支付记录失败！" + "paymentRecordId is: " + paymentRecordResult.getId());
            }
        }
    }
}
