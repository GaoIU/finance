package com.fanteng.finance.cms.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.fanteng.util.EncryptUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Value("${sys.user.default.avatar}")
	private String default_avatar;

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(SysUser sysUser) throws Exception {
		String password = sysUser.getPassword();
		if (StringUtil.isEmpty(password)) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "用户密码不能为空");
		}

		if (password.length() < 6 || password.length() > 16) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "用户密码的长度只能在6-16位之间");
		}

		password = EncryptUtil.encodeByBC(password);
		sysUser.setPassword(password);
		sysUser.setAvatar(default_avatar);
		String sysUserId = save(sysUser).toString();

		if (StringUtil.isNotBlank(sysUserId)) {
			if (StringUtil.isNotBlank(sysUser.getSysRoleIds())) {
				String[] sysRoleIds = sysUser.getSysRoleIds().split(",");
				if (ArrayUtils.isNotEmpty(sysRoleIds)) {
					for (String sysRoleId : sysRoleIds) {
						SysUserRole sysUserRole = new SysUserRole(sysRoleId, sysUserId);
						sysUserRoleService.save(sysUserRole);
					}
				}
			}

			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
	}

}
