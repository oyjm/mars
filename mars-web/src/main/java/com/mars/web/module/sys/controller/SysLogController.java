package com.mars.web.module.sys.controller;

import com.mars.common.utils.MarsResponse;
import com.mars.web.common.utils.PageUtils;
import com.mars.web.module.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 系统日志
 *
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public MarsResponse list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);

		return MarsResponse.ok().put("page", page);
	}
	
}
