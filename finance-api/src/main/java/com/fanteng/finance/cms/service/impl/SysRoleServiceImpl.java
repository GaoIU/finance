package com.fanteng.finance.cms.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysRoleDao;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.util.StringUtil;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

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

}
