package com.lxh.trend.job;

import cn.hutool.core.date.DateUtil;
import com.lxh.trend.pojo.Index;
import com.lxh.trend.service.IndexDataService;
import com.lxh.trend.service.IndexService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/*
 * @PackageName: com.lxh.trend.util
 * @ClassName: IndexDataSyncJob
 * @Description:
 * @author: 辉
 * @date: 2020/2/18 22:04
 * */
public class IndexDataSyncJob extends QuartzJobBean {
    @Autowired
    private IndexService indexService;

    @Autowired
    private IndexDataService indexDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("定时启动：" + DateUtil.now());
        List<Index> indexes = indexService.fresh();
        for (Index index : indexes) {
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时结束：" + DateUtil.now());

    }
}
