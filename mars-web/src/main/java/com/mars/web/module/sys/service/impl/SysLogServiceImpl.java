package com.mars.web.module.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.mars.web.common.utils.PageUtils;
import com.mars.web.common.utils.Query;
import com.mars.web.module.sys.bean.SysLog;
import com.mars.web.module.sys.mapper.SysLogMapper;
import com.mars.web.module.sys.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        Page<SysLog> page = this.selectPage(
            new Query<SysLog>(params).getPage(),
            new EntityWrapper<SysLog>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }
}
