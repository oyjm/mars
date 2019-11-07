package com.mars.web.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * shiro权限标签
 * @author oliver
 * @email jmouyang@hotmail.com
 * @date 2019年10月29日 下午16:56:19
 */
@Component
public class ShiroTag {

	/**
	 * 是否拥有该权限
	 * @param permission  权限标识
	 * @return   true：是     false：否
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}

}
