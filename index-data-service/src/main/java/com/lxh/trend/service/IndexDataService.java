package com.lxh.trend.service;

import cn.hutool.core.collection.CollUtil;
import com.lxh.trend.pojo.IndexData;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.service
 * @ClassName: IndexDataService
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:50
 * */
@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {

    @Cacheable(key = "'indexData-code-'+ #p0")
    public List<IndexData> get(String code) {
        return CollUtil.toList();
    }
}
