package com.fanteng.finance.cms.user.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {

	/**
	 * 获取用户列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

	/**
	 * 禁用或启用用户
	 * 
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	boolean usable(String id, Short status) throws Exception;

}
