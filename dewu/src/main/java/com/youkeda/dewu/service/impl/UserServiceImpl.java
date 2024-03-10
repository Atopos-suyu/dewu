package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.UserDAO;
import com.youkeda.dewu.dataobject.UserDO;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.model.User;
import com.youkeda.dewu.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component  //用于任何需要被 Spring 管理的类上
public class UserServiceImpl implements UserService {

    @Autowired  //自动装配 Spring Bean，它可以自动地在应用程序的上下文中寻找匹配某个类型的 Bean，并将其注入到需要的地方
    private UserDAO userDAO;

    @Override  //方法重写
    public Result<User> register(String userName, String pwd) {
        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = userDAO.findByUserName(userName);
        if (userDO != null) {
            result.setCode("602");
            result.setMessage("用户名已经存在");
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "_ykd2050";
        // 生成md5值，并转大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        UserDO userDO1 = new UserDO();
        userDO1.setUserName(userName);
        // 初始昵称等于用户名
        userDO1.setNickName(userName);
        userDO1.setPwd(md5Pwd);

        userDAO.add(userDO1);  //将用户信息存入数据库

        result.setSuccess(true);

        result.setData(userDO1.toModel());  //将新注册用户的模型对象设置到结果中

        return result;
    }

    @Override
    public Result<User> login(String userName, String pwd) {

        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = userDAO.findByUserName(userName);
        if (userDO == null) {
            result.setCode("602");
            result.setMessage("用户名不存在");
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "_ykd2050";
        // 生成md5值，并转大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        if (!StringUtils.equals(md5Pwd, userDO.getPwd())) {
            result.setCode("603");
            result.setMessage("密码不对");
            return result;
        }

        result.setSuccess(true);

        result.setData(userDO.toModel());

        return result;
    }

    //StringUtils 专注于字符串的处理，而 CollectionUtils 则专注于集合的处理
    @Override
    public List<User> queryUser(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return null;
        }
        List<UserDO> userDOS = userDAO.findByIds(userIds);  //根据用户ID列表从数据库中查询用户信息，将查询结果存储在userDOS列表中
        List<User> users = new ArrayList<>();  //创建一个空的用户列表users 遍历userDOS列表，将UserDO对象转换为对应的User对象并添加到users列表中
        if (!CollectionUtils.isEmpty(userDOS)) {
            for (UserDO userDO : userDOS) {
                users.add(userDO.toModel());
            }
        }
        return users;
    }
}
