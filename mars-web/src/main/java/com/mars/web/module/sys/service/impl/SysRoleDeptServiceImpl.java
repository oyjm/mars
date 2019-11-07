package com.mars.web.module.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.mars.web.module.sys.bean.SysRoleDept;
import com.mars.web.module.sys.mapper.SysRoleDeptMapper;
import com.mars.web.module.sys.service.SysRoleDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与部门对应关系
 *
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		//先删除角色与部门关系
		deleteBatch(new Long[]{roleId});

		if(deptIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleDept> list = new ArrayList<>(deptIdList.size());
		for(Long deptId : deptIdList){
			SysRoleDept SysRoleDept = new SysRoleDept();
			SysRoleDept.setDeptId(deptId);
			SysRoleDept.setRoleId(roleId);

			list.add(SysRoleDept);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryDeptIdList(Long[] roleIds) {
		return baseMapper.queryDeptIdList(roleIds);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
