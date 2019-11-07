package com.mars.web.module.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.mars.web.common.annotation.DataFilter;
import com.mars.web.common.shiro.ShiroUtils;
import com.mars.web.common.utils.Constant;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.common.utils.Query;
import com.mars.web.module.sys.bean.SysDept;
import com.mars.web.module.sys.bean.SysUser;
import com.mars.web.module.sys.mapper.SysUserMapper;
import com.mars.web.module.sys.service.SysDeptService;
import com.mars.web.module.sys.service.SysUserRoleService;
import com.mars.web.module.sys.service.SysUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");

		Page<SysUser> page = this.selectPage(
			new Query<SysUser>(params).getPage(),
			new EntityWrapper<SysUser>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
		);

		for(SysUser SysUser : page.getRecords()){
			SysDept sysDeptEntity = sysDeptService.selectById(SysUser.getDeptId());
			SysUser.setDeptName(sysDeptEntity.getName());
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysUser user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.insert(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUser user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			SysUser userEntity = this.selectById(user.getUserId());
			user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
		}
		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUser userEntity = new SysUser();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUser>().eq("user_id", userId).eq("password", password));
    }

}
