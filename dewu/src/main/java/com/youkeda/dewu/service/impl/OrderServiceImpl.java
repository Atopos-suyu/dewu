package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.OrderDAO;
import com.youkeda.dewu.dataobject.OrderDO;
import com.youkeda.dewu.model.*;
import com.youkeda.dewu.param.QueryOrderParam;
import com.youkeda.dewu.service.OrderService;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.dewu.service.UserService;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RedissonClient redisson;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public Order add(Order order) {

        if (order == null) {
            return null;
        }
        order.setId(UUIDUtils.getUUID());
        order.setStatus(OrderStatus.WAIT_BUYER_PAY);  //表示待买家付款
        //生成唯一订单号
        order.setOrderNumber(createOrderNumber());
        OrderDO orderDO = new OrderDO(order);
        int insert = orderDAO.insert(orderDO);  //插入订单数据到数据库中
        if (insert == 1) {
            return order;
        }
        return null;
    }

    @Override
    public Order getByOrderNumber(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            return null;
        }
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO != null) {
            Order order = orderDO.convertToModel();
            return order;
        }
        return null;
    }  //根据订单号查询订单信息，并将查询到的订单以Order对象的形式返回，失败或传入的订单号为空则返回空

    @Override
    public Order updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        //更改订单状态
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO == null) {
            return null;
        }
        orderDO.setStatus(orderStatus.toString());  //转换为字符串形式并设置给orderDO对象的状态属性
        orderDAO.update(orderDO);
        return orderDO.convertToModel();
    }  //根据订单号更新订单的状态并返回更新后的订单对象

    @Override
    public Order updateProductPersonNumber(String orderNumber) {
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO == null) {
            return null;
        }
        //获取分布式锁
        RLock transferLock = redisson.getLock("PURCHASE");
        transferLock.lock();
        try {
            ProductDetail productDetail = productDetailService.get(orderDO.getProductDetailId());
            if (productDetail == null) {
                return null;
            }
            productDetail.setStock(productDetail.getStock() - 1);
            productDetailService.save(productDetail);
            Product product = productService.get(productDetail.getProductId());
            product.setPurchaseNum(product.getPurchaseNum() + 1);
            productService.save(product);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            transferLock.unlock();  //无论更新操作是否成功，代码都会释放分布式锁
        }
        return orderDO.convertToModel();
    }  //根据订单号更新产品的库存和购买人数信息，并返回更新后的订单对象

    @Override
    public Paging<Order> queryRecentPaySuccess(QueryOrderParam queryOrderParam) {
        Paging<Order> result = new Paging<>();

        if (queryOrderParam == null) {
            queryOrderParam = new QueryOrderParam();
        }

        int counts = orderDAO.selectCounts(queryOrderParam);  //查询符合条件的订单总数，并将结果赋值给counts

        if (counts < 1) {
            result.setTotalCount(0);
            return result;
        }  //查询到的订单总数小于1，将分页结果对象的总记录数设为0，并返回空的分页结果

        List<OrderDO> orderDOS = orderDAO.pageQuery(queryOrderParam);
        List<Order> orders = new ArrayList<>();
        List<String> productDetailIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        for (OrderDO orderDO : orderDOS) {
            orders.add(orderDO.convertToModel());
            productDetailIds.add(orderDO.getProductDetailId());
            userIds.add(Long.parseLong(orderDO.getUserId()));
        }
        //遍历查询到的订单信息，将每个 OrderDO 对象转换为 Order 对象，并分别将产品详情ID和用户ID添加到对应的列表中

        List<User> users = userService.queryUser(userIds);
        List<ProductDetail> productDetails = productDetailService.queryProductDetail(productDetailIds);
        Map<String, ProductDetail> productDetailMap = productDetails.stream().collect(
            Collectors.toMap(ProductDetail::getId, t -> t));
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, t -> t));

        for (Order order : orders) {
            order.setProductDetail(productDetailMap.get(order.getProductDetailId()));
            order.setUser(userMap.get(Long.parseLong(order.getUserId())));
        }  //将查询到的产品详情信息和用户信息转换为Map对象，方便后续根据ID快速查找

        result.setData(orders);
        result.setTotalCount(counts);
        result.setPageNum(orders.size());
        result.setPageSize(queryOrderParam.getPageSize());
        result.setTotalPage(orders.size() / queryOrderParam.getPageNum());  //设置分页结果对象的数据、总记录数、当前页记录数、页面大小和总页数，并返回分页结果对象
        return result;
    }  //根据查询条件查询最近支付成功的订单信息，并返回分页结果。在查询过程中，还会查询订单相关的产品详情信息和用户信息，并将其填充到订单对象中

    /**
     * 生成唯一订单号
     *
     * @return String
     */
    private String createOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String date = dtf2.format(localDateTime);
        RAtomicLong aLong = redisson.getAtomicLong("myOrderPartNum" + date);
        aLong.expire(10, TimeUnit.SECONDS);
        long count = aLong.incrementAndGet();
        String orderId = date + count;
        return orderId;
    }  //生成一个唯一的订单号，保证在一定时间内不会重复
}
