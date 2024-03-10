package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

//典型的MyBatis Mapper接口定义，用于定义数据库操作的方法
//通过MyBatis框架可以将这些方法映射到对应的SQL语句执行，实现与数据库的交互
@Mapper
public interface UserDAO {

    int batchAdd(@Param("list") List<UserDO> userDOs);  //批量添加用户信息，接收一个UserDO对象列表作为参数，并将其批量插入数据库

    List<UserDO> findByIds(@Param("ids") List<Long> ids);  //根据用户ID列表查询用户信息

    int add(UserDO userDO);  //添加单个用户信息

    int update(UserDO userDO);  //更新用户信息

    int delete(@Param("id") long id);  //根据用户ID删除用户信息

    List<UserDO> findAll();  //查询所有用户信息

    UserDO findByUserName(@Param("userName") String name);  //根据用户名查询用户信息

    List<UserDO> query(@Param("keyWord") String keyWord);  //根据关键字查询用户信息

    List<UserDO> search(@Param("keyWord") String keyWord, @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);  //根据关键字和时间范围搜索用户信息
}
