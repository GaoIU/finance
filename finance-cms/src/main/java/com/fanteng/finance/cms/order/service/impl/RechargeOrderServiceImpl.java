package com.fanteng.finance.cms.order.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.order.dao.RechargeOrderDao;
import com.fanteng.finance.cms.order.service.RechargeOrderService;
import com.fanteng.finance.cms.user.service.LogUserAccountService;
import com.fanteng.finance.cms.user.service.UserAccountService;
import com.fanteng.finance.entity.LogUserAccount;
import com.fanteng.finance.entity.RechargeOrder;
import com.fanteng.finance.entity.UserAccount;
import com.fanteng.util.DateUtil;
import com.fanteng.util.ExcelUtil;
import com.fanteng.util.StringUtil;

@Service
public class RechargeOrderServiceImpl extends BaseServiceImpl<RechargeOrderDao, RechargeOrder>
		implements RechargeOrderService {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private LogUserAccountService logUserAccountService;

	/**
	 * 获取充值列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult queryList(Map<String, Object> params) throws Exception {
		Integer current = MapUtils.getInteger(params, "current");
		Integer size = MapUtils.getInteger(params, "size");
		String userName = MapUtils.getString(params, "userName");
		Short status = MapUtils.getShort(params, "status");
		Short type = MapUtils.getShort(params, "type");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(userName)) {
			Condition condition = new Condition("userName", Operation.LIKE_ANY, userName);
			conditions.add(condition);
		}
		if (status != null) {
			Condition condition = new Condition("status", Operation.EQ, status);
			conditions.add(condition);
		}
		if (type != null) {
			Condition condition = new Condition("type", Operation.EQ, type);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			Date begin = DateUtil.toDate(beginTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.GE, new Timestamp(begin.getTime()));
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(endTime)) {
			Date end = DateUtil.toDate(endTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.LE, new Timestamp(end.getTime()));
			conditions.add(condition);
		}

		Page page = findPage(current, size, conditions);
		Map<String, Object> data = new HashMap<>(0);
		data.put("page", page);
		data.put("condition", params);

		return new JsonResult(HttpStatus.OK, "操作成功", data);
	}

	/**
	 * 审核订单
	 * 
	 * @param rechargeOrder
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult audit(RechargeOrder rechargeOrder) {
		boolean audit = update(rechargeOrder);
		if (audit) {
			if (rechargeOrder.getStatus() == RechargeOrder.STATUS_REVIEW_PASS) {
				UserAccount userAccount = userAccountService.findOne("userId", Operation.EQ, rechargeOrder.getUserId());
				String userAccountId = userAccount.getId();
				BigDecimal amount = userAccount.getAmount();
				BigDecimal availableAmount = userAccount.getAvailableAmount();

				amount = amount.add(rechargeOrder.getAmount());
				availableAmount = availableAmount.add(rechargeOrder.getAmount());
				userAccount.setAmount(amount);
				userAccount.setAvailableAmount(availableAmount);
				if (userAccountService.update(userAccount)) {
					LogUserAccount logUserAccount = new LogUserAccount(rechargeOrder.getAmount(),
							LogUserAccount.OPERATION_TYPE_RECHARGE, userAccountId, rechargeOrder.getUserId());
					logUserAccountService.save(logUserAccount);
				}
			}

			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", audit);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 导出Excel
	 * 
	 * @param response
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void export(HttpServletResponse response, Map<String, Object> params) throws Exception {
		String fileName = MapUtils.getString(params, "fileName");
		String keys = MapUtils.getString(params, "keys");
		String columNames = MapUtils.getString(params, "columNames");
		String replace = MapUtils.getString(params, "replace");

		Integer current = MapUtils.getInteger(params, "current");
		Integer size = MapUtils.getInteger(params, "size");
		String userName = MapUtils.getString(params, "userName");
		Short status = MapUtils.getShort(params, "status");
		Short type = MapUtils.getShort(params, "type");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(userName)) {
			Condition condition = new Condition("userName", Operation.LIKE_ANY, userName);
			conditions.add(condition);
		}
		if (status != null) {
			Condition condition = new Condition("status", Operation.EQ, status);
			conditions.add(condition);
		}
		if (type != null) {
			Condition condition = new Condition("type", Operation.EQ, type);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			Date begin = DateUtil.toDate(beginTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.GE, new Timestamp(begin.getTime()));
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(endTime)) {
			Date end = DateUtil.toDate(endTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.LE, new Timestamp(end.getTime()));
			conditions.add(condition);
		}

		List<?> list = findPage(current, size, conditions).getList();
		ExcelUtil.export(response, fileName, list, keys, columNames, replace);
	}

}
