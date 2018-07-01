package com.fanteng.finance.cms.service.impl;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysUserDao;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 注册后台用户
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(Map<String, Object> param) throws Exception {
		SysUser sysUser = (SysUser) BeanUtil.mapToBean(param, SysUser.class);
		sysUser = checkSysUser(sysUser);

		String sysUserId = save(sysUser).toString();
		if (StringUtil.isNotBlank(sysUserId)) {
			String[] sysRoleIds = MapUtils.getString(param, "sysRoleIds").split(",");
			if (ArrayUtils.isNotEmpty(sysRoleIds)) {
				for (String sysRoleId : sysRoleIds) {
					SysUserRole sysUserRole = new SysUserRole(sysRoleId, sysUserId);
					sysUserRoleService.save(sysUserRole);
				}
			}

			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
	}

	private SysUser checkSysUser(@Valid SysUser sysUser) {
		return sysUser;
	}

}
