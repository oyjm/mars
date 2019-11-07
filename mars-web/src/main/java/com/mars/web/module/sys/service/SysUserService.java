package com.mars.web.module.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysUser;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 */
public interface SysUserService extends IService<SysUser> {

	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 保存用户
	 */
	void save(SysUser user);
	
	/**
	 * 修改用户
	 */
	void update(SysUser user);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
}
