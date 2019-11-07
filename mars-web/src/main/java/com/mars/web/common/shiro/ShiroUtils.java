package com.mars.web.common.shiro;

import com.mars.common.exception.MarsException;
import com.mars.web.module.sys.bean.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具
 * @author oliver
 * @email jmouyang@hotmail.com
 * @date 2019年10月29日 下午16:56:19
 */
public class ShiroUtils {
	//加密算法
	public final static String hashAlgorithmName = "SHA-256";
	//循环次数
	public final static int hashIterations = 16;

	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUser() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUser().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new MarsException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

}
