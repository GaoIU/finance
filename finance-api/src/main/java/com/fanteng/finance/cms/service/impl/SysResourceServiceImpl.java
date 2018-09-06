package com.fanteng.finance.cms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysResourceDao;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceDao, SysResource> implements SysResourceService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	@Autowired
	private SysRoleService sysRoleService;

	@Value("${sys.role.admin.code}")
	private String ADMINISTRATOR;

	/**
	 * 根据用户ID获取所有资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	@Override
	public List<SysResource> getResourcesBySysUserId(String sysUserId) {
		boolean hasPermission = false;

		List<SysRole> sysRoles = sysRoleService.getSysRolesBySysUserId(sysUserId);
		for (SysRole sysRole : sysRoles) {
			if (StringUtil.equals(ADMINISTRATOR, sysRole.getCode())) {
				hasPermission = true;
				break;
			}
		}

		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.status_normal);
		conditions.add(status);

		if (hasPermission) {
			return findAll(conditions);
		}

		List<SysUserRole> sysUserRoles = sysUserRoleService.findOnes("sysUserId", Operation.EQ, sysUserId);
		if (CollectionUtils.isNotEmpty(sysUserRoles)) {
			List<String> sysRoleIds = new ArrayList<String>(0);
			for (SysUserRole sysUserRole : sysUserRoles) {
				sysRoleIds.add(sysUserRole.getSysRoleId());
			}

			List<SysRoleResource> sysRoleResourceIds = sysRoleResourceService.findOnes("sysRoleId", Operation.IN,
					sysRoleIds);
			if (CollectionUtils.isNotEmpty(sysRoleResourceIds)) {
				List<String> sysResourceIds = new ArrayList<String>(0);
				for (SysRoleResource sysRoleResource : sysRoleResourceIds) {
					sysResourceIds.add(sysRoleResource.getSysResourceId());
				}

				Condition ids = new Condition("id", Operation.IN, sysResourceIds);
				conditions.add(ids);

				return findAll(conditions);
			}
		}

		return null;
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
		SysResource sysResource = findOne(propertyName, Operation.EQ, value);

		if (sysResource != null) {
			return true;
		}

		return false;
	}

	/**
	 * 根据后台用户ID获取所有父级菜单资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	@Override
	public List<SysResource> getResource(String sysUserId) {
		boolean hasPermission = false;

		List<SysRole> sysRoles = sysRoleService.getSysRolesBySysUserId(sysUserId);
		for (SysRole sysRole : sysRoles) {
			if (StringUtil.equals(ADMINISTRATOR, sysRole.getCode())) {
				hasPermission = true;
				break;
			}
		}

		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.status_normal);
		Condition type = new Condition("type", Operation.EQ, SysResource.type_menu);
		Condition parentId = new Condition("parentId", Operation.IS_NULL, null);
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(status);
		conditions.add(type);
		conditions.add(parentId);
		conditions.add(sort);
		conditions.add(createTime);

		if (hasPermission) {
			return findAll(conditions);
		}

		List<SysUserRole> sysUserRoles = sysUserRoleService.findOnes("sysUserId", Operation.EQ, sysUserId);
		if (CollectionUtils.isNotEmpty(sysUserRoles)) {
			List<String> sysRoleIds = new ArrayList<String>(0);
			for (SysUserRole sysUserRole : sysUserRoles) {
				sysRoleIds.add(sysUserRole.getSysRoleId());
			}

			List<SysRoleResource> sysRoleResourceIds = sysRoleResourceService.findOnes("sysRoleId", Operation.IN,
					sysRoleIds);
			if (CollectionUtils.isNotEmpty(sysRoleResourceIds)) {
				List<String> sysResourceIds = new ArrayList<String>(0);
				for (SysRoleResource sysRoleResource : sysRoleResourceIds) {
					sysResourceIds.add(sysRoleResource.getSysResourceId());
				}

				Condition ids = new Condition("id", Operation.IN, sysResourceIds);
				conditions.add(ids);

				return findAll(conditions);
			}
		}

		return null;
	}

	/**
	 * 获取树形菜单
	 * 
	 * @param list
	 * @param childName
	 * @return
	 */
	@Override
	public List<Object> getMenu(List<SysResource> list, String childName) {
		List<Object> menu = new ArrayList<>(0);
		for (SysResource sysResource : list) {
			Map<String, Object> map = new LinkedHashMap<String, Object>(0);
			map = BeanUtil.toMap(sysResource);
			map.put(childName, menuChild(sysResource.getId(), childName));
			menu.add(map);
		}
		return menu;
	}

	/**
	 * 递归处理菜单
	 * 
	 * @param id
	 * @param childName
	 * @return
	 */
	private List<?> menuChild(String id, String childName) {
		List<Object> list = new ArrayList<>(0);

		List<SysResource> menu = getMenuByParentId(id);
		for (SysResource sysResource : menu) {
			Map<String, Object> child = new LinkedHashMap<String, Object>(0);
			child = BeanUtil.toMap(sysResource);
			child.put(childName, menuChild(sysResource.getId(), childName));
			list.add(child);
		}

		return list;
	}

	/**
	 * 根据父级ID查询菜单
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<SysResource> getMenuByParentId(String id) {
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.status_normal);
		Condition type = new Condition("type", Operation.EQ, SysResource.type_menu);
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		Condition parentId = new Condition("parentId", Operation.EQ, id);
		conditions.add(status);
		conditions.add(type);
		conditions.add(sort);
		conditions.add(createTime);
		conditions.add(parentId);
		return findAll(conditions);
	}

	/**
	 * 获取后台资源列表
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
		Short type = MapUtils.getShort(params, "type");
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
