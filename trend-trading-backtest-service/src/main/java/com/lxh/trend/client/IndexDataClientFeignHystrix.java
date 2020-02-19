package com.lxh.trend.client;

import cn.hutool.core.collection.CollectionUtil;
import com.lxh.trend.pojo.IndexData;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.client
 * @ClassName: IndexDataClientFeignHystrix
 * @Description:
 * @author: 辉
 * @date: 2020/2/19 0:05
 * */
@Component
public class IndexDataClientFeignHystrix implements IndexDataClient{
    @Override
    public List<IndexData> getIndexData(String code) {
        IndexData indexData = new IndexData();
        indexData.setClosePoint(0);
        indexData.setDate("0000-00-00");
        return CollectionUtil.toList(indexData);
    }
}
