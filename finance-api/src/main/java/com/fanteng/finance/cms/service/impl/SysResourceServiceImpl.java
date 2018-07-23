package com.fanteng.finance.cms.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
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
	 * 根据后台用户ID获取所有菜单资源
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
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(status);
		conditions.add(type);
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
	 * @return
	 */
	@Override
	public List<Object> getMenu(List<SysResource> list) {
		List<Object> menu = new ArrayList<>(0);
		for (SysResource sysResource : list) {
			Map<String, Object> map = new LinkedHashMap<String, Object>(0);
			map = BeanUtil.toMap(sysResource);
			map.put("childList", menuChild(sysResource.getId()));
			menu.add(map);
		}
		return menu;
	}

	/**
	 * 递归处理菜单
	 * 
	 * @param id
	 * @return
	 */
	private List<?> menuChild(String id) {
		List<Object> list = new ArrayList<>(0);

		List<SysResource> menu = getMenuByParentId(id);
		for (SysResource sysResource : menu) {
			Map<String, Object> child = new LinkedHashMap<String, Object>(0);
			child = BeanUtil.toMap(sysResource);
			child.put("childList", menuChild(sysResource.getId()));
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

}
