package com.mars.web.module.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysDict;

import java.util.Map;

/**
 * 数据字典
 *
 */
public interface SysDictService extends IService<SysDict> {

    PageUtils queryPage(Map<String, Object> params);
}

