package com.lxh.trend.controller;

import com.lxh.trend.pojo.Index;
import com.lxh.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.controller
 * @ClassName: IndexController
 * @Description:
 * @author: 辉
 * @date: 2020/2/17 0:06
 * */
@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/freshCodes")
    public List<Index> fresh() throws Exception {
        return indexService.fresh();
    }
    @GetMapping("/getCodes")
    public List<Index> get() throws Exception {
        return indexService.get();
    }
    @GetMapping("/removeCodes")
    public String remove() throws Exception {
        indexService.remove();
        return "remove codes successfully";
    }
}
