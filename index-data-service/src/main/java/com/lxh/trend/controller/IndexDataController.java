package com.lxh.trend.controller;

import com.lxh.trend.config.IpConfiguration;
import com.lxh.trend.pojo.IndexData;
import com.lxh.trend.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.controller
 * @ClassName: IndexDataController
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:53
 * */
@RestController
public class IndexDataController {
    @Autowired
    IndexDataService indexDataService;
    @Autowired
    IpConfiguration ipConfiguration;

//  http://127.0.0.1:8021/data/000300

    @GetMapping("/data/{code}")
    public List<IndexData> get(@PathVariable("code") String code) throws Exception {
        System.out.println("current instance is :" + ipConfiguration.getPort());
        return indexDataService.get(code);
    }
}
