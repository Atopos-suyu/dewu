package com.youkeda.dewu.dataobject;

import com.youkeda.dewu.model.User;

import java.time.LocalDateTime;

//用户实体类 UserDO，用于表示用户的基本信息
public class UserDO {

    private long id;

    private String userName;

    private String pwd;

    private String nickName;

    private String avatar;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * DO 转换为 Model
     *
     * @return
     */

     //将 UserDO 对象转换为 User 对象的方法 toModel()
    public User toModel() {
        User user = new User();
        user.setId(getId());
        user.setUserName(getUserName());
        user.setNickName(getNickName());
        user.setAvatar(getAvatar());
        user.setGmtCreated(getGmtCreated());
        user.setGmtModified(getGmtModified());
        return user;
    }//这样的设计有助于实现数据模型的隔离和解耦，使得不同层的对象可以根据需要进行转换和处理，同时也有助于提高代码的可复用性和可维护性
}
