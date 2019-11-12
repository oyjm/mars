package com.mars.schedule.mapper;

import com.mars.schedule.bean.SysScheduleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysScheduleInfoMapper {
    List<SysScheduleInfo> list(SysScheduleInfo sysScheduleInfo);
    SysScheduleInfo findById(@Param("id") String id);
    int save(SysScheduleInfo sysScheduleInfo);
    int update(SysScheduleInfo sysScheduleInfo);
    int delete(@Param("id") String id);
}
