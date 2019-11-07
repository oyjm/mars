package com.mars.web.module.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysRole;

import java.util.Map;


/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRole> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRole role);

	void update(SysRole role);
	
	void deleteBatch(Long[] roleIds);

}
