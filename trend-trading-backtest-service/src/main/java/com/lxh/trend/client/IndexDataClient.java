package com.lxh.trend.client;

import com.lxh.trend.pojo.IndexData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.client
 * @ClassName: IndexDataClient
 * @Description:
 * @author: 辉
 * @date: 2020/2/19 0:02
 * */
@FeignClient(value = "INDEX-DATA-SERVICE", fallback = IndexDataClientFeignHystrix.class)
public interface IndexDataClient {
    @GetMapping("/data/{code}")
    public List<IndexData> getIndexData(@PathVariable("code") String code);
}
