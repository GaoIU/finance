package com.fanteng.finance.cms.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysUser;

public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 注册后台用户
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	JsonResult register(Map<String, Object> param) throws Exception;

}
