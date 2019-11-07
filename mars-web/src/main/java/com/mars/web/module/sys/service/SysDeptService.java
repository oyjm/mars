package com.mars.web.module.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.mars.web.module.sys.bean.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 */
public interface SysDeptService extends IService<SysDept> {

	List<SysDept> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);

}
