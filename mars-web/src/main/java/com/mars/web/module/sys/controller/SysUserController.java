package com.mars.web.module.sys.controller;


import com.mars.common.utils.MarsResponse;
import com.mars.common.validator.Assert;
import com.mars.common.validator.ValidatorUtils;
import com.mars.common.validator.group.AddGroup;
import com.mars.common.validator.group.UpdateGroup;
import com.mars.web.common.annotation.SysLog;
import com.mars.web.common.shiro.ShiroUtils;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysUser;
import com.mars.web.module.sys.service.SysUserRoleService;
import com.mars.web.module.sys.service.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public MarsResponse list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);

		return MarsResponse.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public MarsResponse info(){
		return MarsResponse.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public MarsResponse password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return MarsResponse.error("原密码不正确");
		}
		
		return MarsResponse.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public MarsResponse info(@PathVariable("userId") Long userId){
		SysUser user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return MarsResponse.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public MarsResponse save(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.save(user);
		
		return MarsResponse.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public MarsResponse update(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return MarsResponse.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public MarsResponse delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return MarsResponse.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return MarsResponse.error("当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));
		
		return MarsResponse.ok();
	}
}
