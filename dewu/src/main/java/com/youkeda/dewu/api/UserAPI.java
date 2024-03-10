package com.youkeda.dewu.api;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.model.User;
import com.youkeda.dewu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author joe
 */

@RequestMapping("/api/user")
@Controller  //与@RequestMapping注解配合使用，指定处理请求的URL路径
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    @ResponseBody  //告诉Spring MVC框架，不要去寻找对应的视图（View），而是直接将方法的返回值序列化为HTTP响应体（Response Body）。通常用于返回JSON或XML数据
    public Result<User> reg(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd) {
        return userService.register(userName, pwd);  //@RequestParam注解用来绑定HTTP请求参数到方法参数，
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd, HttpServletRequest request) {
        Result<User> result = userService.login(userName, pwd);

        //将用户的ID存储到当前会话的session中，这样可以在后续的请求中标识用户身份
        if (result.isSuccess()) {
            request.getSession().setAttribute("userId", result.getData().getId());  
        }
   
        return result;
    }

    @GetMapping("/logout")
    @ResponseBody
    //HttpServletRequest对象，用于获取当前会话的session
    public Result logout(HttpServletRequest request) {
        //result对象用来存储登出操作的结果信息·
        Result result = new Result();
        request.getSession().removeAttribute("userId");

        result.setSuccess(true);
        return result;
    }


}
