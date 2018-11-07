package com.fanteng.finance.cms.statistics.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.RedisClient;
import com.fanteng.finance.cms.order.service.RechargeOrderService;
import com.fanteng.finance.cms.properties.RedisCommonKeyProperties;
import com.fanteng.finance.cms.statistics.service.StatisticsService;
import com.fanteng.finance.entity.RechargeOrder;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private RechargeOrderService rechargeOrderService;

	@Autowired
	private RedisClient redisClient;

	/**
	 * 充值统计
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public JsonResult recharge(Map<String, Object> params) {
		Double ratio = Double.valueOf(redisClient.get(RedisCommonKeyProperties.AMOUNT_PROPORTION));
		Short type = MapUtils.getShort(params, "type");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");

		String sql = "SELECT SUM(CASE WHEN ro.type = :alipay THEN ro.amount ELSE 0 END) alipay, SUM(CASE WHEN ro.type = :wechat THEN ro.amount ELSE 0 END) wechat, SUM(CASE WHEN ro.type = :bankcard THEN ro.amount ELSE 0 END) bankcard, SUM(ro.amount) sum FROM recharge_order ro WHERE ro.`status` = :status";
		StringBuffer sb = new StringBuffer(sql);
		Map<String, Object> map = new HashMap<>(0);
		map.put("alipay", RechargeOrder.TYPE_ALIPAY);
		map.put("wechat", RechargeOrder.TYPE_WECHAT);
		map.put("bankcard", RechargeOrder.TYPE_BANK_CARD);
		map.put("status", RechargeOrder.STATUS_REVIEW_PASS);

		if (type != null) {
			sb.append(" AND ro.type = :type");
			map.put("type", type);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			sb.append(" AND ro.create_time >= :beginTime");
			map.put("beginTime", beginTime);
		}
		if (StringUtil.isNotBlank(endTime)) {
			sb.append(" AND ro.create_time <= :endTime");
			map.put("endTime", endTime);
		}
		Map<String, Object> item = rechargeOrderService.queryOneToMap(sb.toString(), map);
		item.forEach((k, v) -> {
			item.put(k, ((BigDecimal) v).divide(BigDecimal.valueOf(ratio)));
		});

		List<Map<String, Object>> list = new ArrayList<>(0);
		String sumSql = "SELECT SUM(amount) amount FROM recharge_order WHERE `status` = :status AND DATE(create_time) = :date";
		Map<String, Object> condition = new HashMap<>(0);
		condition.put("status", RechargeOrder.STATUS_REVIEW_PASS);
		for (int i = -6; i <= 0; i++) {
			Map<String, Object> record = new HashMap<>(0);
			String date = DateUtil.toString(DateUtil.addDays(new Date(), i), "yyyy-MM-dd");
			condition.put("date", date);
			Long amount = MapUtils.getLong(rechargeOrderService.queryOneToMap(sumSql, condition), "amount");
			amount = amount == null ? 0 : amount;
			record.put(date, amount / ratio);
			list.add(record);
		}

		Map<String, Object> data = new HashMap<>(0);
		data.put("item", item);
		data.put("list", list);

		return new JsonResult(HttpStatus.OK, "操作成功", data);
	}

}
