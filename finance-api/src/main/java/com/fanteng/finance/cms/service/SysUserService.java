package com.fanteng.finance.cms.service;

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

}
