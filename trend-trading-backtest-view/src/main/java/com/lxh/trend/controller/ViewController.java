package com.lxh.trend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * @PackageName: com.lxh.trend.controller
 * @ClassName: ViewController
 * @Description:
 * @author: 辉
 * @date: 2020/2/19 0:23
 * */
@Controller
@RefreshScope
public class ViewController {
    @Value("${version}")
    String version;
    @GetMapping("/")
    public String view(Model m) throws Exception {
        m.addAttribute("version", version);
        return "view";
    }
}
