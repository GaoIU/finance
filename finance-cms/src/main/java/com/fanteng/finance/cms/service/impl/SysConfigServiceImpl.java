package com.fanteng.finance.cms.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.RedisClient;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysConfigDao;
import com.fanteng.finance.cms.service.SysConfigService;
import com.fanteng.finance.entity.SysConfig;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

	@Autowired
	private RedisClient redisClient;

	@Transactional(rollbackFor = Exception.class)
	@PostConstruct
	public void init() {
		List<SysConfig> list = findOnes("status", Operation.EQ, SysConfig.STATUS_NORMAL);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysConfig sysConfig : list) {
				redisClient.set(sysConfig.getCode(), sysConfig.getContent());
			}
		}
	}

	/**
	 * 获取后台系统配置列表
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
		String code = MapUtils.getString(params, "code");
		Short status = MapUtils.getShort(params, "status");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(name)) {
			Condition condition = new Condition("name", Operation.LIKE_ANY, name);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(code)) {
			Condition condition = new Condition("code", Operation.LIKE_ANY, code.toUpperCase());
			conditions.add(condition);
		}
		if (status != null) {
			Condition condition = new Condition("status", Operation.EQ, status);
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

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public boolean checkPropertyName(String propertyName, Object value) {
		return false;
	}

	/**
	 * 验证配置编码
	 * 
	 * @param code
	 * @param sysConfigId
	 * @return
	 */
	@Override
	public boolean checkCode(String code, String sysConfigId) {
		boolean checkCode = checkPropertyName("code", code);
		if (StringUtil.isNotBlank(sysConfigId)) {
			SysConfig sysConfig = get(sysConfigId);
			if (sysConfig != null) {
				if (StringUtil.equals(code, sysConfig.getCode())) {
					checkCode = false;
				}
			}
		}

		return checkCode;
	}

	/**
	 * 添加后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(SysConfig sysConfig) {
		Serializable id = save(sysConfig);
		if (id != null) {
			if (sysConfig.getStatus() == SysConfig.STATUS_NORMAL) {
				redisClient.set(sysConfig.getCode(), sysConfig.getContent());
			}
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 修改后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult edit(SysConfig sysConfig) {
		boolean b = updateIgnore(sysConfig);
		if (b) {
			if (sysConfig.getStatus() == SysConfig.STATUS_NORMAL) {
				redisClient.set(sysConfig.getCode(), sysConfig.getContent());
			} else {
				redisClient.del(sysConfig.getCode());
			}

			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 批量删除后台系统配置
	 * 
	 * @param ids
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void del(String[] ids) {
		for (String id : ids) {
			SysConfig sysConfig = get(id);
			String code = sysConfig.getCode();
			boolean b = delete(sysConfig);
			if (b) {
				redisClient.del(code);
			}
		}
	}

}
