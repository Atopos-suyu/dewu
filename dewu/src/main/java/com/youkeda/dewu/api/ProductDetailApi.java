package com.youkeda.dewu.api;

import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * TODO
 *
 * @author zr
 * @date 2020/7/14, 周二
 */
@Controller
@RequestMapping("/api/productdetail")
public class ProductDetailApi {

    @Autowired
    private ProductDetailService productDetailService;

    //处理获取产品详细信息的请求，然后将获取到的信息包装到Result对象中返回给前端
    @GetMapping("/productId")
    @ResponseBody
    public Result<List<ProductDetail>> getProductDetails(@RequestParam("productId") String productId) {

        Result<List<ProductDetail>> result = new Result<>();

        List<ProductDetail> productDetails = productDetailService.getByProductId(productId);  //根据产品ID获取对应的产品详情信息

        result.setData(productDetails);
        result.setSuccess(true);
        return result;

    }
}
