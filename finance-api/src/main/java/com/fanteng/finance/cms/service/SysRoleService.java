package com.fanteng.finance.cms.service;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysRole;

public interface SysRoleService extends BaseService<SysRole> {

	/**
	 * 添加后台角色
	 * 
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	JsonResult register(SysRole sysRole) throws Exception;

}
