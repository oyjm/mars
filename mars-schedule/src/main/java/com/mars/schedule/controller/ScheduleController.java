package com.mars.schedule.controller;

import com.mars.schedule.bean.SysScheduleInfo;
import com.mars.schedule.common.MarsResponse;
import com.mars.schedule.common.PageUtils;
import com.mars.schedule.common.Query;
import com.mars.schedule.service.SysScheduleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    @Autowired
    private SysScheduleInfoService sysScheduleInfoService;

    @ResponseBody
    @RequestMapping("/list")
    public MarsResponse list(@RequestParam Map<String, Object> params){
        PageUtils pageUtil = sysScheduleInfoService.queryList(new Query(params));
        return MarsResponse.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public MarsResponse info(@PathVariable("id") String id){
        SysScheduleInfo sysScheduleInfo = sysScheduleInfoService.findById(id);
        return MarsResponse.ok().put("sysScheduleInfo", sysScheduleInfo);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public MarsResponse save(@RequestBody SysScheduleInfo sysScheduleInfo){
        sysScheduleInfoService.save(sysScheduleInfo);
        return MarsResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public MarsResponse update(@RequestBody SysScheduleInfo sysScheduleInfo){
        sysScheduleInfoService.update(sysScheduleInfo);
        return MarsResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public MarsResponse delete(@RequestBody String[] ids){
        for(String id:ids){
            sysScheduleInfoService.delete(id);
        }
        return MarsResponse.ok();
    }

    @RequestMapping("/updateIsStart")
    public MarsResponse updateIsStart(@RequestBody SysScheduleInfo sysScheduleInfo){
        sysScheduleInfoService.updateIsStart(sysScheduleInfo);
        return MarsResponse.ok();
    }

    @RequestMapping("/updateStatus")
    public MarsResponse updateStatus(@RequestBody SysScheduleInfo sysScheduleInfo){
        sysScheduleInfoService.updateStatus(sysScheduleInfo);
        return MarsResponse.ok();
    }

}
