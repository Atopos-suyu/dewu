package com.youkeda.dewu.service;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.model.User;

import java.util.List;

public interface UserService {

    /**
     * 注册用户
     *
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> register(String userName, String pwd);

    /**
     * 执行登录逻辑，登录成功返回 User 对象
     *
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> login(String userName, String pwd);

    /**
     * 获取多个用户信息
     *
     * @param userIds  查询参数
     * @return
     */
    public List<User> queryUser(List<Long> userIds);
}
