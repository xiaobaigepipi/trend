package com.lxh.trend.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *在spring容器中已存在的实例需要重新获取一下，ApplicationContextAware这个类就是当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean。换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 *
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil() {
        System.out.println("SpringContextUtil()");
    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        System.out.println("applicationContext:"+applicationContext);
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
