package com.mars.schedule.job;

import com.mars.schedule.service.SysScheduleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testJob")
public class TestJob {
    @Autowired
    private SysScheduleInfoService sysScheduleInfoService;

    public void run(String param1){
        System.out.println("param1: "+param1);
//        if(sysScheduleInfoService!=null)
//            System.out.println(sysScheduleInfoService.listAll().size());
    }
}
