package com.mars.schedule.quartz;

import com.mars.schedule.common.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class SystemJobFactory implements Job {
    Logger log = LoggerFactory.getLogger(SystemJobFactory.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        log.debug(jobDetail.getKey().getName()+"="+jobDetail.getKey().getGroup());
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        //具体处理类组件名（处理类上加了@Component()注解，注意默认首字母数据库存的时候是小写）
        String className = jobDataMap.getString("className");
        //具体处理类的方法名
        String methodName = jobDataMap.getString("methodName");
        //参数
        String params = jobDataMap.getString("params");
        //获取对应的Bean
        Object object = SpringUtil.getBean(className);
        try {
            //利用反射执行对应方法
            Method method = object.getClass().getMethod(methodName);
            if(StringUtils.isBlank(params)){
                method.invoke(object);
            }else{
                String[] args = params.split(",");
                method.invoke(object,args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
