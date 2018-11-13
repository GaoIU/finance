package com.fanteng.finance.cms.order.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.RechargeOrder;

public interface RechargeOrderService extends BaseService<RechargeOrder> {

	/**
	 * 获取充值列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

	/**
	 * 审核订单
	 * 
	 * @param rechargeOrder
	 * @return
	 */
	JsonResult audit(RechargeOrder rechargeOrder);

	/**
	 * 导出Excel
	 * 
	 * @param response
	 * @param params
	 * @throws Exception
	 */
	void export(HttpServletResponse response, Map<String, Object> params) throws Exception;

}
