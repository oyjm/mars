package com.mars.schedule.quartz;

import com.alibaba.fastjson.JSONObject;
import com.mars.schedule.bean.SysScheduleInfo;
import com.mars.schedule.common.SpringUtil;
import com.mars.schedule.job.Job;
import com.mars.schedule.mapper.SysScheduleInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SysScheduleInfoMapper sysScheduleInfoMapper;

    @Override
    public void run(String... args) throws Exception {
        List<SysScheduleInfo> list = sysScheduleInfoMapper.listAllEnable();
        for(SysScheduleInfo sysScheduleInfo:list){
            quartzService.deleteJob(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());
        }
        HashMap<String,Object> map = new HashMap<>();
        for(SysScheduleInfo sysScheduleInfo:list){
            if(StringUtils.isNotBlank(sysScheduleInfo.getParams())){
                map = JSONObject.parseObject(sysScheduleInfo.getParams(),HashMap.class);
            }
            quartzService.addJob(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup(), sysScheduleInfo.getCron(), map);
        }

//        map.put("name",1);
//        quartzService.deleteJob("job", "test");
//        quartzService.addJob(Job.class, "job", "test", "0 * * * * ?", map);
//
//        map.put("name",2);
//        quartzService.deleteJob("job2", "test");
//        quartzService.addJob(Job.class, "job2", "test", "10 * * * * ?", map);
//
//        map.put("name",3);
//        quartzService.deleteJob("job3", "test2");
//        quartzService.addJob(Job.class, "job3", "test2", "15 * * * * ?", map);
    }
}
