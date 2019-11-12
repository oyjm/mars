package com.mars.schedule.service;

import com.mars.schedule.bean.SysScheduleInfo;
import com.mars.schedule.mapper.SysScheduleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysScheduleInfoService {
    @Autowired
    private SysScheduleInfoMapper sysScheduleInfoMapper;

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
        return sysScheduleInfoMapper.save(sysScheduleInfo);
    }

    public int update(SysScheduleInfo sysScheduleInfo){
        return sysScheduleInfoMapper.update(sysScheduleInfo);
    }

    public int delete(String id){
        return sysScheduleInfoMapper.delete(id);
    }
}
