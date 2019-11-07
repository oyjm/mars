package com.mars.web.module.sys.controller;


import com.mars.common.utils.MarsResponse;
import com.mars.common.validator.ValidatorUtils;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.bean.SysDict;
import com.mars.web.module.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public MarsResponse list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);

        return MarsResponse.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public MarsResponse info(@PathVariable("id") Long id){
        SysDict dict = sysDictService.selectById(id);

        return MarsResponse.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public MarsResponse save(@RequestBody SysDict dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.insert(dict);

        return MarsResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public MarsResponse update(@RequestBody SysDict dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return MarsResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public MarsResponse delete(@RequestBody Long[] ids){
        sysDictService.deleteBatchIds(Arrays.asList(ids));

        return MarsResponse.ok();
    }

}
