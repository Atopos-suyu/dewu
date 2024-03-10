package com.youkeda.dewu.api;

import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.QueryOrderParam;
import com.youkeda.dewu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单Api。将订单信息添加到数据库中，并返回包含操作结果和订单信息的Result对象
     *
     * @return Result
     */
    @PostMapping(path = "/add")
    @ResponseBody
    public Result<Order> payOrder(@RequestBody Order order) {
        Result<Order> result = new Result();
        result.setSuccess(true);
        if (order == null) {
            result.setSuccess(false);
            result.setMessage("order is null");
            return result;
        }
        Order orderResult = orderService.add(order);  //将订单信息添加到数据库中，并将返回的订单对象赋给orderResult
        result.setData(orderResult);
        return result;
    }

    /**
     * 查询支付成功订单APi
     *
     * @return Result
     */
    @GetMapping(path = "/queryrecentpaysuccess")
    @ResponseBody
    public Result<Paging<Order>> queryRecentPaySuccess(@RequestBody QueryOrderParam queryOrderParam) {
        Result<Paging<Order>> result = new Result();
        result.setSuccess(true);
        if (queryOrderParam == null) {
            result.setSuccess(false);
            result.setMessage("queryOrderParam is null");
            return result;
        }

        result.setData(orderService.queryRecentPaySuccess(queryOrderParam));
        return result;
    }
}
