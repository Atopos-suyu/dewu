package com.youkeda.dewu.util;

import java.util.UUID;

//方便生成不带横线的UUID字符串，通常可以用于生成唯一标识符
public class UUIDUtils {
    public static final String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    //UUID.randomUUID().toString() 会生成一个标准的 UUID 字符串，形如 "xxxxxxxx-xxxx
    //replace("-", "") 调用会将生成的 UUID 字符串中的横线 "-" 替换为空字符串，以去除横线
}
