package com.fanteng.finance.cms.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysUser;

public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	JsonResult register(SysUser sysUser) throws Exception;

	/**
	 * 后台用户登录
	 * 
	 * @param param
	 * @return
	 */
	JsonResult signIn(Map<String, Object> param);

}
