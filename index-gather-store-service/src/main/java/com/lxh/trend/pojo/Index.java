package com.lxh.trend.pojo;

import java.io.Serializable;

/*
 * @PackageName: com.lxh.trend.pojo
 * @ClassName: Index
 * @Description:
 * @author: 辉
 * @date: 2020/2/16 23:54
 * */
public class Index implements Serializable {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
