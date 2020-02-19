package com.lxh.trend.pojo;

/*
 * @PackageName: com.lxh.trend.pojo
 * @ClassName: IndexData
 * @Description:
 * @author: 辉
 * @date: 2020/2/19 0:01
 * */
public class IndexData {
    String date;
    float closePoint;
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
