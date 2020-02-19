package com.lxh.trend.pojo;

import java.io.Serializable;

/*
 * @PackageName: com.lxh.trend.pojo
 * @ClassName: IndexData
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:49
 * */
public class IndexData implements Serializable {
    private String date;
    private float closePoint;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getClosePoint() {
        return closePoint;
    }

    public void setClosePoint(float closePoint) {
        this.closePoint = closePoint;
    }
}
