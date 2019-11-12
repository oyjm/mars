package com.mars.schedule.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component("testjob")
public class Job  extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(QuartzJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("------springbootquartzonejob执行" + jobDataMap.get("name").toString() + "###############" + jobExecutionContext.getTrigger());

    }
}
