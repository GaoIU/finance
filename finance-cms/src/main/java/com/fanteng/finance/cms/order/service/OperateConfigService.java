package com.fanteng.finance.cms.order.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.OperateConfig;

public interface OperateConfigService extends BaseService<OperateConfig> {

	/**
	 * 获取充值-提现操作配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

}
