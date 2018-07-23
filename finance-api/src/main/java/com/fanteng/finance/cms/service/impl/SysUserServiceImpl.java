package com.fanteng.finance.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.dao.SysUserDao;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.BeanUtil;
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

		boolean checkMobile = checkPropertyName("mobile", sysUser.getMobile());
		if (checkMobile) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "该手机号已存在");
		}

		boolean checkNickName = checkPropertyName("nickName", sysUser.getNickName());
		if (checkNickName) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "该昵称已被使用");
		}

		boolean checkUserName = checkPropertyName("userName", sysUser.getUserName());
		if (checkUserName) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "该账号已被使用");
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

	/**
	 * 后台用户登录
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public JsonResult signIn(Map<String, Object> param) {
		String userName = MapUtils.getString(param, "userName");
		String password = MapUtils.getString(param, "password");

		if (StringUtil.isBlank(userName)) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "请输入用户名");
		}

		if (StringUtil.isEmpty(password)) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "请输入密码");
		}

		SysUser sysUser = findOne("userName", Operation.EQ, userName);
		if (sysUser != null) {
			if (EncryptUtil.matchesByBC(password, sysUser.getPassword())) {
				if (sysUser.getStatus() == SysUser.status_normal) {
					return new JsonResult(HttpStatus.OK, "登录成功，正在跳转...", sysUser);
				}
				return new JsonResult(HttpStatus.FORBIDDEN, "该用户已被禁用，请联系管理员");
			}
		}
		return new JsonResult(HttpStatus.METHOD_NOT_ALLOWED, "用户名或密码错误");
	}

	/**
	 * 获取后台用户列表
	 * 
	 * @return
	 */
	@Override
	public JsonResult queryList() {
		List<SysUser> list = findAll();

		if (CollectionUtils.isNotEmpty(list)) {
			List<Map<String, Object>> maps = new ArrayList<>(0);
			for (SysUser sysUser : list) {
				Map<String, Object> map = BeanUtil.toMapFiters(sysUser, "password, sysRoleIds");
				maps.add(map);
			}

			return new JsonResult(HttpStatus.OK, "操作成功", maps);
		}

		return new JsonResult(HttpStatus.OK, "操作成功", list);
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
		SysUser sysUser = findOne(propertyName, Operation.EQ, value);

		if (sysUser != null) {
			return true;
		}

		return false;
	}

}
