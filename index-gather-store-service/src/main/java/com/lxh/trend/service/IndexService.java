package com.lxh.trend.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.lxh.trend.pojo.Index;
import com.lxh.trend.util.SpringContextUtil;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * @PackageName: com.lxh.trend.service
 * @ClassName: IndexService
 * @Description:
 * @author: 辉
 * @date: 2020/2/16 23:55
 * */
@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    public List<Index> indexes;
    @Autowired
    RestTemplate restTemplate;

    //这表示如果fetch_indexes_from_third_part获取失败了，就自动调用 third_part_not_connected 并返回。
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<Index> fresh() {
        indexes = gatherData();
        IndexService indexService = SpringContextUtil.getBean(IndexService.class);
        indexService.remove();
        return indexService.store();
    }

    /**
     spring框架提供的RestTemplate类可用于在应用中调用rest服务，它简化了与http服务的通信方式，统一了RESTful的标准，封装了http链接， 我们只需要传入url及返回值类型即可。相较于之前常用的HttpClient，RestTemplate是一种更优雅的调用RESTful服务的方式。*/
    public List<Index> gatherData() {
        //获取项目中文件内容，参数是访问地址和返回类型
        List<Map> list = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return maptoIndex(list);
    }

    //删除redis中的缓存
    @CacheEvict(allEntries=true)
    public void remove(){

    }

    //储存all_codes内容，如果redis存在，则不会刷新redis中的缓存，不存在返回新的内容到redis中
    @Cacheable(key="'all_codes'")
    public List<Index> store(){
        System.out.println(this);
        return indexes;
    }

    //获取redis中indexes内容，如果没有，则返回null列表内容
    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        return CollUtil.toList();
    }

    public List<Index> third_part_not_connected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }

    //转化数组类型
    public List<Index> maptoIndex(List<Map> maps) {
        List<Index> list = new ArrayList<>();
        for (Map map : maps) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            list.add(index);
        }
        return list;
    }
}
