package com.mars.web.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mars.web.module.sys.bean.SysMenu;

import java.util.List;

/***
 * 系统菜单数据库查询接口
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

}
