package com.mars.web.module.sys.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.mars.common.utils.MapUtils;
import com.mars.web.common.utils.Constant;
import com.mars.web.module.sys.bean.SysMenu;
import com.mars.web.module.sys.mapper.SysMenuMapper;
import com.mars.web.module.sys.service.SysMenuService;
import com.mars.web.module.sys.service.SysRoleMenuService;
import com.mars.web.module.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Override
	public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenu> userMenuList = new ArrayList<>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenu> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.deleteById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.deleteByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		
		for(SysMenu entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
