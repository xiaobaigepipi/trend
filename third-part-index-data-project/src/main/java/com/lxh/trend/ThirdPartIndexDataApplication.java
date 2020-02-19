package com.lxh.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient //表示注册为微服务
public class ThirdPartIndexDataApplication {
    public static void main( String[] args ) {
        int port = 8090;
        int eurekaServerPort = 8761;

        //检测eureka服务器有没有启动
        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort );
            System.exit(1);
        }

        //检测启动时是否携带了参数，有的话就以该参数作为端口号，比如携带参数为port=8099，之后本次启动就以8099端口号启动，这样就很方便
        if(null!=args && 0!=args.length) {
            for (String arg : args) {
                if(arg.startsWith("port=")) {
                    String strPort= StrUtil.subAfter(arg, "port=", true);
                    if(NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        //判断8090端口号是否被占用
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port=" + port).run(args);
    }
}
