package com.lxh.trend.service;

import cn.hutool.core.collection.CollUtil;
import com.lxh.trend.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.service
 * @ClassName: IndexService
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:32
 * */
@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    private List<Index> indexes;

    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        Index index = new Index();
        index.setName("无效指数代码");
        index.setCode("000000");
        return CollUtil.toList(index);
    }
}
