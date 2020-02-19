package com.lxh.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main( String[] args ) {
        int port = 8761; //这个是eureka的默认端口号，不要修改，后面的子项目都会访问这个端口号
        if (!NetUtil.isUsableLocalPort(port)) {
            System.out.println("端口号" + port + "已被占用");
            System.exit(1);
        }
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" + port).run(args);
    }
}
