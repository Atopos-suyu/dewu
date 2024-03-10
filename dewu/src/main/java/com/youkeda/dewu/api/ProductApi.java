package com.youkeda.dewu.api;

import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

//定义全局路由前缀
@RequestMapping("/api/product")
@Controller
public class ProductApi {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/page")
    public Result<Paging<Product>> pageQuery(BasePageParam param) {

        Result<Paging<Product>> result = new Result<>();

        result.setSuccess(true);
        result.setData(productService.pageQueryProduct(param));  //获取产品信息的分页查询，并将该查询结果封装到Result对象中，以便在API接口的响应中返回给前端
        return result;
    }

}
