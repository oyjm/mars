package com.mars.web.module.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.common.utils.Query;
import com.mars.web.module.sys.bean.SysDict;
import com.mars.web.module.sys.mapper.SysDictMapper;
import com.mars.web.module.sys.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<SysDict> page = this.selectPage(
                new Query<SysDict>(params).getPage(),
                new EntityWrapper<SysDict>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
