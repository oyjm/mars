package com.mars.web.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mars.web.module.sys.bean.SysRoleDept;

import java.util.List;

/**
 * 角色与部门对应关系
 *
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long[] roleIds);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
