package com.mars.web.module.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysLog;

import java.util.Map;


/**
 * 系统日志
 *
 */
public interface SysLogService extends IService<SysLog> {

    PageUtils queryPage(Map<String, Object> params);

}
