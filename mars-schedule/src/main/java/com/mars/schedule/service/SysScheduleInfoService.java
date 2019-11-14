package com.mars.schedule.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mars.schedule.bean.SysScheduleInfo;
import com.mars.schedule.common.PageUtils;
import com.mars.schedule.common.Query;
import com.mars.schedule.mapper.SysScheduleInfoMapper;
import com.mars.schedule.quartz.QuartzService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysScheduleInfoService {
    @Autowired
    private SysScheduleInfoMapper sysScheduleInfoMapper;

    @Autowired
    private QuartzService quartzService;

    public PageUtils queryList(Query query) {
        Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysScheduleInfo> list = sysScheduleInfoMapper.list(null);
        return new PageUtils(list, (int)page.getTotal(), query.getLimit(), query.getPage());
    }

    public List<SysScheduleInfo> listAllEnable(){
        SysScheduleInfo sysScheduleInfo = new SysScheduleInfo();
        sysScheduleInfo.setStatus(0);
        sysScheduleInfo.setIsStart(1);
        return sysScheduleInfoMapper.list(sysScheduleInfo);
    }

    public List<SysScheduleInfo> listAll(){
        return sysScheduleInfoMapper.list(null);
    }

    public SysScheduleInfo findById(String id){
        return sysScheduleInfoMapper.findById(id);
    }

    public int save(SysScheduleInfo sysScheduleInfo){
        sysScheduleInfo.setId(MD5Encoder.encode((sysScheduleInfo.getTaskGroup()+sysScheduleInfo.getTaskName()).getBytes()));
        sysScheduleInfo.setIsStart(0);
        sysScheduleInfo.setStatus(0);
        return sysScheduleInfoMapper.save(sysScheduleInfo);
    }

    public int update(SysScheduleInfo sysScheduleInfo){
        return sysScheduleInfoMapper.update(sysScheduleInfo);
    }

    public int delete(String id){
        SysScheduleInfo sysScheduleInfo = sysScheduleInfoMapper.findById(id);
        if(sysScheduleInfo.getIsStart()==1 && sysScheduleInfo.getStatus()==0){
            quartzService.deleteJob(sysScheduleInfo);
        }
        return sysScheduleInfoMapper.delete(id);
    }

    public void updateIsStart(SysScheduleInfo sysScheduleInfo){
        SysScheduleInfo current = sysScheduleInfoMapper.findById(sysScheduleInfo.getId());
        if(sysScheduleInfo.getIsStart() == current.getIsStart()){
            return;
        }
        quartzService.deleteJob(current);
        if(sysScheduleInfo.getIsStart() == 1) {
            quartzService.addJob(current);
        }
        sysScheduleInfoMapper.update(sysScheduleInfo);
    }

    public void updateStatus(SysScheduleInfo sysScheduleInfo){
        SysScheduleInfo current = sysScheduleInfoMapper.findById(sysScheduleInfo.getId());
        if(sysScheduleInfo.getStatus() == current.getStatus()){
            return;
        }
        if(sysScheduleInfo.getStatus() == 1) {
            quartzService.deleteJob(current);
            sysScheduleInfo.setIsStart(0);
        }
        sysScheduleInfoMapper.update(sysScheduleInfo);
    }
}
