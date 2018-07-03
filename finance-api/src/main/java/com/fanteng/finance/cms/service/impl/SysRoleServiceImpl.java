package com.fanteng.finance.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysRoleDao;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.finance.entity.SysUserRole;
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
						SysRoleResource sysRoleResource = new SysRoleResource(sysResourceId, sysRoleId);
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
			Condition status = new Condition("status", Operation.NE, SysRole.status_disable);
			conditions.add(ids);
			conditions.add(status);

			return findAll(conditions);
		}
		return null;
	}

}
