package com.fanteng.finance.cms.user.service.impl;

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
import com.fanteng.finance.cms.user.dao.LogUserAccountDao;
import com.fanteng.finance.cms.user.service.LogUserAccountService;
import com.fanteng.finance.entity.LogUserAccount;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class LogUserAccountServiceImpl extends BaseServiceImpl<LogUserAccountDao, LogUserAccount>
		implements LogUserAccountService {

	/**
	 * 查询用户账户明细
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
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		Short operationType = MapUtils.getShort(params, "operationType");
		List<Condition> conditions = new ArrayList<>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(userName)) {
			Condition condition = new Condition("userName", Operation.LIKE_ANY, userName);
			conditions.add(condition);
		}
		if (operationType != null) {
			Condition condition = new Condition("operationType", Operation.EQ, operationType);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			Date begin = DateUtil.toDate(beginTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.GE, new Timestamp(begin.getTime()));
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(endTime)) {
			Date end = DateUtil.toDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
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
