package com.mars.web.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mars.web.module.sys.bean.SysUser;

import java.util.List;

/***
 * 系统用户数据库查询接口
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

}
