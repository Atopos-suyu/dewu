package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.ProductDAO;
import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

//如果需要判断一个字符串是否为 null 或者长度为 0 或者只包含空白字符，推荐使用 StringUtils.isBlank 方法
//如果只需要判断字符串是否为 null 或者长度为 0，可以使用 StringUtils.isEmpty 方法

//实现 ProductService 接口中的方法
@Service  //标识该类作为服务的实现类，实现了接口中定义的方法
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    //UUIDUtils 是一个自定义的工具类，用于生成唯一识别码
    @Override  //根据传入的商品对象执行保存（插入或更新）操作
    public int save(Product product) {

        if (StringUtils.isBlank(product.getId())) {
            product.setId(UUIDUtils.getUUID());  //如果传入的商品对象的id为空，将其生成一个UUID，作为新商品的id
            return productDAO.insert(new ProductDO(product));  //插入新的商品记录
        }

        return productDAO.updateByPrimaryKey(new ProductDO(product));  //否则更新已有商品的记录
    }

     //根据传入的商品 id 获取对应的商品信息
    @Override
    public Product get(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        ProductDO productDO = productDAO.selectByPrimaryKey(id);  //获取具有指定 id 的商品记录，并将结果保存在 productDO 变量中
        if (productDO == null) {
            return null;
        }
        return productDO.convertToModel();
    }

    @Override
    public Paging<Product> pageQueryProduct(BasePageParam param) {

        Paging<Product> result = new Paging<>();
        if (param == null) {
            return result;
        }
        //确保分页索引不会小于 0
        if (param.getPagination() < 0) {
            param.setPagination(0);
        }
        //确保每页数据条数不会小于 0
        if (param.getPageSize() < 0) {
            param.setPageSize(10);
        }

        //查询数据总数
        int counts = productDAO.selectAllCounts();
        if (counts < 0) {
            return result;
        }

        //组装返回数据。返回给客户端的分页数据
        result.setTotalCount(counts);  //用于表示总共有多少条数据
        result.setPageSize(param.getPageSize());  //表示每页显示多少条数据
        result.setPageNum(param.getPagination());  //表示当前返回的是第几页的数据

        int totalPage = (int)Math.ceil(counts / (param.getPageSize() * 1.0));  //得到总页数后用Math()方法向上取整确保页数是一个整数值
        result.setTotalPage(totalPage);

        //实际返回的数据
        List<ProductDO> productDOS = productDAO.pageQuery(param);  //进行分页查询操作，将获取当前页的商品数据列表保存在productDOS中
        List<Product> productList = new ArrayList<>();  //创建一个空的productList列表，用于存储转换后的Product对象
        if (!CollectionUtils.isEmpty(productDOS)) {
            for (ProductDO productDO : productDOS) {
                productList.add(productDO.convertToModel());  //遍历将每个ProductDO对象转换为Product对象并添加到ProductList中
            }
        }

        result.setData(productList);  //将处理完的商品数据列表设置到result对象的data属性中

        return result;
    }
}
