package com.fanteng.finance.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysResourceDao;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.finance.entity.SysUserRole;

@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceDao, SysResource> implements SysResourceService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	/**
	 * 根据用户ID获取所有资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	@Override
	public List<SysResource> getResourcesBySysUserId(String sysUserId) {
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

				List<Condition> conditions = new ArrayList<Condition>(0);
				Condition ids = new Condition("id", Operation.IN, sysResourceIds);
				Condition status = new Condition("status", Operation.NE, SysResource.status_disable);
				Condition orderBy = new Condition("sort", Operation.ASC, "sort");
				conditions.add(ids);
				conditions.add(status);
				conditions.add(orderBy);
				
				return findAll(conditions);
			}
		}

		return null;
	}

}
