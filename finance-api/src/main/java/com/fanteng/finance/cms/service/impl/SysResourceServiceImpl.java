package com.fanteng.finance.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

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
		Condition orderBy = new Condition("sort", Operation.ASC, "sort");
		conditions.add(status);
		conditions.add(orderBy);

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

}
