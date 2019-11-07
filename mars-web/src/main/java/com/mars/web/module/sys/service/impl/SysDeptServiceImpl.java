package com.mars.web.module.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.mars.web.common.annotation.DataFilter;
import com.mars.web.common.utils.Constant;
import com.mars.web.module.sys.bean.SysDept;
import com.mars.web.module.sys.mapper.SysDeptMapper;
import com.mars.web.module.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
	
	@Override
	@DataFilter(subDept = true, user = false)
	public List<SysDept> queryList(Map<String, Object> params){
		List<SysDept> deptList =
			this.selectList(new EntityWrapper<SysDept>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));

		for(SysDept SysDept : deptList){
			SysDept parentDeptEntity =  this.selectById(SysDept.getParentId());
			if(parentDeptEntity != null){
				SysDept.setParentName(parentDeptEntity.getName());
			}
		}
		return deptList;
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
