package com.youkeda;

import com.youkeda.dewu.dao.ProductDAO;
import com.youkeda.dewu.dao.ProductDetailDAO;
import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.dewu.dataobject.ProductDetailDO;
import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.OrderStatus;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.service.OrderService;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.dewu.util.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YkdTest {
    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Test
    void contextLoads() {
        //插入商品信息
        ProductDO product = new ProductDO();
        product.setId(UUIDUtils.getUUID());
        product.setName("测试商品");
        product.setPrice(20.0);
        product.setPurchaseNum(20);
        int insertSize = productDAO.insert(product);
        if (insertSize < 1) {
            error("插入商品失败！");
            return;
        }

        //插入商品详情信息
        ProductDetailDO productDetailDO = new ProductDetailDO();
        productDetailDO.setPrice(20.0);
        productDetailDO.setProductId(product.getId());
        productDetailDO.setSize(20.0);
        productDetailDO.setId(UUIDUtils.getUUID());
        int insertSize2 = productDetailDAO.insert(productDetailDO);
        if (insertSize2 < 1) {
            error("插入商品详情失败！");
            return;
        }

        //插入订单信息
        Order order = new Order();
        order.setTotalPrice(1.0);
        order.setStatus(OrderStatus.TRADE_PAID_SUCCESS);
        order.setId(UUIDUtils.getUUID());
        order.setProductDetailId(productDetailDO.getId());
        order.setOrderNumber(UUIDUtils.getUUID());
        order.setProductDetail(productDetailDO.convertToModel());
        Order addOrder = orderService.add(order);
        if (addOrder == null) {
            error("插入订单信息失败！");
            return;
        }

        Order updateOrder = orderService.updateProductPersonNumber(order.getOrderNumber());
        if (updateOrder == null) {
            error("插入商品付款人数失败！");
            return;
        }
    }

}
