package com.lxh.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
 * @PackageName: com.lxh.trend
 * @ClassName: IndexGatherStoreApplication
 * @Description:
 * @author: 辉
 * @date: 2020/2/17 0:07
 * */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix //启动断路器
@EnableCaching
public class IndexGatherStoreApplication {
    public static void main(String[] args) {

        int port = 0;
        int defaultPort = 8001;
        int redisPort = 6379;
        int eurekaServerPort = 8761;
        port = defaultPort ;

        //判断redis是否开启
        if (NetUtil.isUsableLocalPort(redisPort)) {
            System.err.printf("检查到端口%d 未启用，判断 redis 服务器没有启动，本服务无法使用，故退出%n", redisPort );
            System.exit(1);
        }

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

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);

    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
