package com.youkeda.dewu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @Author songchuanming
 * @DATE 2020/7/6.
 */
 //泛型类，，D表示数据的类型，实现Serializable接口表明这个类的实例可以被序列化和反序列化
public class Result<D> implements Serializable {
    @JsonProperty("isSuccess")  //用于指定JSON序列化时success属性对应的JSON字段名为isSuccess
    private boolean success = false;

    private String code;  //用于存储结果的状态码

    private String message;  //用于存储结果的消息

    private D data;  //泛型类型的数据字段，用于存储具体的数据

    //方法签名中的 <T> 表明该方法是一个泛型方法，并且返回类型为 Result<T>
    public static <T> Result<T> create() {
        return new Result<T>();  //创建了一个泛型类型为 T 的 Result 实例，并将其作为方法的返回值
    }

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result<D> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<D> setMessage(String message) {
        this.message = message;
        return this;
    }

    public D getData() {
        return data;
    }

    public Result<D> setData(D data) {
        this.data = data;
        return this;
    }
}
