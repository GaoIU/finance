package com.fanteng.finance.cms.order.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.order.dao.OperateConfigDao;
import com.fanteng.finance.cms.order.service.OperateConfigService;
import com.fanteng.finance.entity.OperateConfig;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class OperateConfigServiceImpl extends BaseServiceImpl<OperateConfigDao, OperateConfig>
		implements OperateConfigService {

	/**
	 * 获取充值-提现操作配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult queryList(Map<String, Object> params) throws Exception {
		Integer current = MapUtils.getInteger(params, "current");
		Integer size = MapUtils.getInteger(params, "size");
		String name = MapUtils.getString(params, "name");
		Short status = MapUtils.getShort(params, "status");
		Short type = MapUtils.getShort(params, "type");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(name)) {
			Condition condition = new Condition("name", Operation.LIKE_ANY, name);
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

}
