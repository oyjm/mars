package com.mars.schedule.quartz;

import com.mars.schedule.common.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;

import java.lang.reflect.Method;

@Slf4j
public class SystemJobFactory implements Job {

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
            if(StringUtils.isBlank(params)){
                //无参数反射
                Method method = object.getClass().getMethod(methodName);
                method.invoke(object);
            }else{
                //有参数反射
                String[] args = params.split(",");
                Class[] c = new Class[args.length];
                for(int i=0;i<args.length;i++){
                    c[i] = String.class;
                }
                Method method = object.getClass().getMethod(methodName,c);
                method.invoke(object,args);
            }
        } catch (Exception e) {
            log.error("excute job method error,",e);
        }
    }
}
