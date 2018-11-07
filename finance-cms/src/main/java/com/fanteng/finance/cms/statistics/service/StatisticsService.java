package com.fanteng.finance.cms.statistics.service;

import java.util.Map;

import com.fanteng.core.JsonResult;

public interface StatisticsService {

	/**
	 * 充值统计
	 * 
	 * @param params
	 * @return
	 */
	JsonResult recharge(Map<String, Object> params);

}
