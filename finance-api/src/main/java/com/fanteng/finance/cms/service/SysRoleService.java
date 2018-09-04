package com.fanteng.finance.cms.service;

import java.util.List;
import java.util.Map;

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

	/**
	 * 根据后台用户ID获取所有的角色
	 * 
	 * @param sysUserId
	 * @return
	 */
	List<SysRole> getSysRolesBySysUserId(String sysUserId);

	/**
	 * 获取后台角色列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

}
