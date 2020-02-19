package com.lxh.trend.controller;

import com.lxh.trend.config.IpConfiguration;
import com.lxh.trend.pojo.Index;
import com.lxh.trend.service.IndexService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.controller
 * @ClassName: IndexController
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:33
 * */
@RestController
public class IndexController {
    @Autowired
    IndexService indexService;
    @Autowired
    IpConfiguration ipConfiguration;

    @GetMapping("/codes")
    @CrossOrigin
    //表示跨域
    public List<Index> getCodes() {
        System.out.println("当前查询端口号是：" + ipConfiguration.getPort());
        return indexService.get();
    }
}
