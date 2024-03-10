package com.youkeda.dewu.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.dewu.dao.UserDAO;
import com.youkeda.dewu.dataobject.UserDO;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//@RequestParam用于从请求URL中获取参数，而@RequestBody用于从请求体中获取参数

/**
* @RequestParam和@RequestBody注解区别
* @RequestParam：
* @RequestParam用于从请求中提取特定名称的参数，并将其赋给方法参数。
* 它主要用于处理查询参数（query parameters）或表单数据中的参数。
* 默认情况下，参数是必需的，如果请求中没有该参数，则会抛出异常。可以通过设置required属性为false来将参数设置为可选。
* 适用于接收简单的参数，如字符串、数字等。
*/

/**
* @RequestBody：
* @RequestBody用于将请求体（body）中的数据绑定到方法参数上，通常用于接收JSON或XML格式的数据。
* 在POST请求中，请求数据通常是放在请求体中的，使用@RequestBody可以将请求体中的数据转换为Java对象。
* Spring会根据请求的Content-Type自动选择合适的HttpMessageConverter来处理请求体的数据，将其转换为对应的Java对象。
* 适用于接收复杂的数据结构，如对象、列表等
*/


//控制前端页面的接口，例如页面的跳转等，会调用Service中的接口
@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/users")
    @ResponseBody
    public Result<Paging<UserDO>> getAll(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Result<Paging<UserDO>> result = new Result();  //用于存储所有用户的结果信息

        //如果不传递，则默认为pageNum=1和pageSize=15
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        //PageHelper工具类设置当前页数为1，以及每页3条记录。 调用startPage()方法指定当前页数和每页记录数
        Page<UserDO> page = PageHelper.startPage(pageNum, pageSize)
        .doSelectPage(() -> userDAO.findAll());  //在doSelectPage()方法中执行查询操作 Lambda表达式（）-> userDAO.findAll()来指定要执行的查询操作，查询所有用户数据

        result.setSuccess(true);
        //将获取到的分页信息封装到Paging对象中，并将该Paging对象设置为Result对象的data属性值
        result.setData(
            new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
        return result;
    }

    @PostMapping("/user")
    @ResponseBody
    public UserDO save(@RequestBody UserDO userDO) {
        userDAO.add(userDO);  //调用useDAO的add方法将接受到的userDO对象存储到数据库中
        return userDO;
    }

    @PostMapping("/user/batchAdd")
    @ResponseBody
    public List<UserDO> batchAdd(@RequestBody List<UserDO> userDOs) {
        userDAO.batchAdd(userDOs);
        return userDOs;
    }

    @PostMapping("/user/update")
    @ResponseBody
    public UserDO update(@RequestBody UserDO userDO) {
        userDAO.update(userDO);
        return userDO;
    }

    @GetMapping("/user/del")
    @ResponseBody
    //从请求参数中获取“id"对应的值将其转化为Long类型的id
    public boolean delete(@RequestParam("id") Long id) {
        return userDAO.delete(id) > 0;  //如果删除成功，delete()方法会返回大于0的值，此时方法返回true
    }

    @GetMapping("/user/findByUserName")
    @ResponseBody
    public UserDO findByUserName(@RequestParam("userName") String userName) {
        return userDAO.findByUserName(userName);
    }

    @GetMapping("/user/search")
    @ResponseBody
    public List<UserDO> search(@RequestParam("keyWord") String keyWord,
                               @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                   LocalDateTime startTime,
                               @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                   LocalDateTime endTime) {
        return userDAO.search(keyWord, startTime, endTime);  //根据关键字和时间范围搜索用户信息，返回符合条件的用户信息列表
    }

    @GetMapping("/user/findByIds")
    @ResponseBody
    public List<UserDO> findByIds(@RequestParam("ids") List<Long> ids) {
        return userDAO.findByIds(ids);
    }
}
