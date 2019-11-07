package com.mars.web.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mars.web.module.sys.bean.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
