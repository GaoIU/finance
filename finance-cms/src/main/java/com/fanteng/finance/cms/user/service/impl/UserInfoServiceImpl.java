package com.fanteng.finance.cms.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Page;
import com.fanteng.core.RedisClient;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.user.dao.UserInfoDao;
import com.fanteng.finance.cms.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.JsonUtil;
import com.fanteng.util.RSAUtil;
import com.fanteng.util.SignatureProperties;
import com.fanteng.util.StringUtil;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

	@Autowired
	private RedisClient redisClient;

	/**
	 * 获取用户列表
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
		Long inviterId = MapUtils.getLong(params, "inviterId");
		Short status = MapUtils.getShort(params, "status");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");

		Map<String, Object> conditions = new HashMap<>(0);
		String sql = "SELECT ui.id, ui.user_name userName, ui.avatar, ui.inviter_id inviterId, ua.amount, ua.available_amount availableAmount, ua.frozen_amount frozenAmount, ui.`status`, ui.create_time createTime FROM user_info ui LEFT JOIN user_account ua ON ui.id = ua.user_id WHERE 1 = 1";
		StringBuffer sb = new StringBuffer(sql);
		if (StringUtil.isNotBlank(userName)) {
			sb.append(" AND ui.user_name LIKE :userName");
			conditions.put("userName", "%" + userName + "%");
		}
		if (inviterId != null) {
			sb.append(" AND ui.inviter_id = :inviterId");
			conditions.put("inviterId", inviterId);
		}
		if (status != null) {
			sb.append(" AND ui.`status` = :status");
			conditions.put("status", status);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			sb.append(" AND DATE(ui.create_time) >= :beginTime");
			conditions.put("beginTime", beginTime);
		}
		if (StringUtil.isNotBlank(endTime)) {
			sb.append(" AND DATE(ui.create_time) <= :endTime");
			conditions.put("endTime", endTime);
		}
		sb.append(" ORDER BY ui.create_time DESC");

		Page page = queryPageToMap(sb.toString(), current, size, conditions);

		Map<String, Object> data = new HashMap<>(0);
		data.put("page", page);
		data.put("condition", params);

		return new JsonResult(HttpStatus.OK, "操作成功", data);
	}

	/**
	 * 禁用或启用用户
	 * 
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean usable(String id, Short status) throws Exception {
		UserInfo userInfo = get(id);
		userInfo.setStatus(status);
		boolean b = update(userInfo);
		if (b && redisClient.exists(id)) {
			Long ttl = redisClient.ttl(id);

			Map<String, Object> map = BeanUtil.toMapIncludes(userInfo, "id, userName, status");
			String token = RSAUtil.encoderByPublicKey(JsonUtil.toJson(map), SignatureProperties.SERVER_PUBLIC_KEY);
			redisClient.setex(id, ttl.intValue(), token);
		}

		return b;
	}

}
