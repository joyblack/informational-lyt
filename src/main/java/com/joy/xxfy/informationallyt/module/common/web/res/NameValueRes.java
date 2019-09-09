package com.joy.xxfy.informationallyt.module.common.web.res;

import lombok.Data;
import lombok.ToString;

/**
 * 只需要回一个值的返回映射实体
 */
@Data
@ToString
public class NameValueRes<T> {
    private String name;
    private T value;

    public NameValueRes() {
    }

    public NameValueRes(String name, T value) {
        this.name = name;
        this.value = value;
    }
}
