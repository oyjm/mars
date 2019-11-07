package com.mars.web.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mars.web.module.sys.bean.SysDept;

import java.util.List;

/**
 * 部门管理
 *
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
