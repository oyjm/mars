package com.mars.web.module.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mars.web.module.sys.bean.SysRoleMenu;
import com.mars.web.module.sys.mapper.SysRoleMenuMapper;
import com.mars.web.module.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
		for(Long menuId : menuIdList){
			SysRoleMenu SysRoleMenu = new SysRoleMenu();
			SysRoleMenu.setMenuId(menuId);
			SysRoleMenu.setRoleId(roleId);

			list.add(SysRoleMenu);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
