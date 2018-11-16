package com.fanteng.finance.cms.user.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.LogUserAccount;

public interface LogUserAccountService extends BaseService<LogUserAccount> {

	/**
	 * 查询用户账户明细
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

}
