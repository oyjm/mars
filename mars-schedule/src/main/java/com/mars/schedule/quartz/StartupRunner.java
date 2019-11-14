package com.mars.schedule.quartz;

import com.alibaba.fastjson.JSONObject;
import com.mars.schedule.bean.SysScheduleInfo;
import com.mars.schedule.service.SysScheduleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SysScheduleInfoService sysScheduleInfoService;

    @Override
    public void run(String... args) throws Exception {
        List<SysScheduleInfo> list = sysScheduleInfoService.listAllEnable();
        for(SysScheduleInfo sysScheduleInfo:list){
            quartzService.deleteJob(sysScheduleInfo);
        }
        for(SysScheduleInfo sysScheduleInfo:list){
            quartzService.addJob(sysScheduleInfo);
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
