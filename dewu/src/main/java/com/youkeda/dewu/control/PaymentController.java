 package com.youkeda.dewu.control;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping(path = "/controller/pay")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PayService payService;

    @GetMapping(path = "/alipayreturnurl")
    String alipayReturnUrl() {
        return "alipayurl";
    }

    @PostMapping(path = "/alipaycallback")
    void alipaycallback(HttpServletRequest request, HttpServletResponse response) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();  //HashMap对象的键值对类型为<String,String> 表示键和值都是字符串类型
        Map requestParams = request.getParameterMap();  //返回一个Map类型包含了请求参数中所有参数名和对应的参数值
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }  //将请求参数的多个值拼接成一个字符串，并将参数名和拼接后的值存储在params HashMap中，以便后续处理和验证
      
        Result payResult = payService.alipayCallBack(params);
        try {
            if (payResult.isSuccess()) {
                response.getWriter().print("success");
                response.getWriter().flush();//可以立即将缓冲区中的内容发送给客户端，而不需要等到缓冲区满或请求处理完成。这对于需要实时地将内容输出给客户端的情况非常有用
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }  //处理支付宝回调，解析支付宝返回的参数，调用支付服务进行相应的处理，根据处理结果给予响应
}
