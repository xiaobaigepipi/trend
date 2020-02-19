package com.lxh.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/*
 * @PackageName: com.lxh.trend.config
 * @ClassName: IpConfiguration
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:46
 * */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {
    private int port;
    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.port = webServerInitializedEvent.getWebServer().getPort();
    }

    public int getPort() {
        return this.port;
    }
}
