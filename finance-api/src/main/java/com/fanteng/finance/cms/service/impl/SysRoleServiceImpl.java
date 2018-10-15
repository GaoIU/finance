package com.fanteng.finance.cms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysRoleDao;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 添加后台角色
	 * 
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(SysRole sysRole) throws Exception {
		String code = sysRole.getCode().toUpperCase();
		sysRole.setCode(code);
		String sysRoleId = save(sysRole).toString();

		if (StringUtil.isNotBlank(sysRoleId)) {
			if (StringUtil.isNotBlank(sysRole.getSysResourceIds())) {
				String[] sysResourceIds = sysRole.getSysResourceIds().split(",");

				if (ArrayUtils.isNotEmpty(sysResourceIds)) {
					for (String sysResourceId : sysResourceIds) {
						SysRoleResource sysRoleResource = new SysRoleResource(sysResourceId.trim(), sysRoleId);
						sysRoleResourceService.save(sysRoleResource);
					}
				}
			}

			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
	}

	/**
	 * 根据后台用户ID获取所有的角色
	 * 
	 * @param sysUserId
	 * @return
	 */
	@Override
	public List<SysRole> getSysRolesBySysUserId(String sysUserId) {
		List<SysUserRole> sysUserRoles = sysUserRoleService.findOnes("sysUserId", Operation.EQ, sysUserId);
		if (CollectionUtils.isNotEmpty(sysUserRoles)) {
			List<String> sysRoleIds = new ArrayList<String>(0);
			for (SysUserRole sysUserRole : sysUserRoles) {
				sysRoleIds.add(sysUserRole.getSysRoleId());
			}

			List<Condition> conditions = new ArrayList<Condition>(0);
			Condition ids = new Condition("id", Operation.IN, sysRoleIds);
			Condition status = new Condition("status", Operation.NE, SysRole.STATUS_DISABLE);
			conditions.add(ids);
			conditions.add(status);

			return findAll(conditions);
		}
		return null;
	}

	/**
	 * 获取后台角色列表
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
	 * 验证角色编码
	 * 
	 * @param code
	 * @param sysRoleId
	 * @return
	 */
	@Override
	public boolean checkCode(String code, String sysRoleId) {
		boolean checkCode = checkPropertyName("code", code);
		if (StringUtil.isNotBlank(sysRoleId)) {
			SysRole sysRole = get(sysRoleId);
			if (sysRole != null) {
				if (StringUtil.equals(code, sysRole.getCode())) {
					checkCode = false;
				}
			}
		}

		return checkCode;
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
		SysRole sysRole = findOne(propertyName, Operation.EQ, value);

		if (sysRole != null) {
			return true;
		}

		return false;
	}

}
