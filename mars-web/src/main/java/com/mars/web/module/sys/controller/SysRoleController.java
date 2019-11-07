package com.mars.web.module.sys.controller;


import com.mars.common.utils.MarsResponse;
import com.mars.common.validator.ValidatorUtils;
import com.mars.web.common.annotation.SysLog;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysRole;
import com.mars.web.module.sys.service.SysRoleDeptService;
import com.mars.web.module.sys.service.SysRoleMenuService;
import com.mars.web.module.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public MarsResponse list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);

		return MarsResponse.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public MarsResponse select(){
		List<SysRole> list = sysRoleService.selectList(null);
		
		return MarsResponse.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public MarsResponse info(@PathVariable("roleId") Long roleId){
		SysRole role = sysRoleService.selectById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);
		
		return MarsResponse.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public MarsResponse save(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.save(role);
		
		return MarsResponse.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public MarsResponse update(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return MarsResponse.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public MarsResponse delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return MarsResponse.ok();
	}
}
