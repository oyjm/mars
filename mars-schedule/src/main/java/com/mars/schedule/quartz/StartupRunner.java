//package com.mars.schedule.quartz;
//
//import com.mars.schedule.bean.SysScheduleInfo;
//import com.mars.schedule.service.SysScheduleInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class StartupRunner implements CommandLineRunner {
//    @Autowired
//    private QuartzService quartzService;
//
//    @Autowired
//    private SysScheduleInfoService sysScheduleInfoService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<SysScheduleInfo> list = sysScheduleInfoService.listAllEnable();
//        for(SysScheduleInfo sysScheduleInfo:list){
//            quartzService.deleteJob(sysScheduleInfo);
//        }
//        for(SysScheduleInfo sysScheduleInfo:list){
//            quartzService.addJob(sysScheduleInfo);
//        }
//
//    }
//}
