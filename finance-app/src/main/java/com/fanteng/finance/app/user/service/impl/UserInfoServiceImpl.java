package com.fanteng.finance.app.user.service.impl;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.app.properties.SignatureProperties;
import com.fanteng.finance.app.user.dao.UserInfoDao;
import com.fanteng.finance.app.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.EncryptUtil;
import com.fanteng.util.RSAUtil;
import com.fanteng.util.StringUtil;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

	@Value("${default.avatar}")
	private String defaultAvatar;

	/**
	 * 用户注册
	 * 
	 * @param userInfo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(UserInfo userInfo) {
		String password = userInfo.getPassword();
		if (StringUtil.isBlank(password) || password.length() < 6 || password.length() > 16) {
			throw new ParamErrorException("密码不能为空且长度只能在6-16位之间");
		}

		boolean checkUserName = checkProperty("userName", userInfo.getUserName());
		if (checkUserName) {
			throw new ParamErrorException("该账号已存在");
		}

		password = EncryptUtil.encodeByBC(password);
		userInfo.setPassword(password);
		userInfo.setMobile(userInfo.getUserName());
		userInfo.setAvatar(defaultAvatar);
		userInfo.setNickName(RandomStringUtils.randomAlphanumeric(8));

		Serializable save = save(userInfo);
		if (save != null) {
			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		return new JsonResult(HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	@Override
	public boolean checkProperty(String property, Object value) {
		UserInfo userInfo = findOne(property, Operation.EQ, value);
		if (userInfo != null) {
			return true;
		}

		return false;
	}

	/**
	 * 用户账号密码登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult signIn(String userName, String password) throws Exception {
		UserInfo userInfo = findOne("userName", Operation.EQ, userName);
		if (userInfo != null) {
			boolean bc = EncryptUtil.matchesByBC(password, userInfo.getPassword());
			if (bc) {
				short status = userInfo.getStatus();
				if (status == UserInfo.STATUS_DISABLE) {
					return new JsonResult(HttpStatus.ACCEPTED, "该账号已被禁用，请联系客服人员");
				}

				String token = RSAUtil.encoderByPublicKey(userInfo.getId(), SignatureProperties.SERVER_PUBLIC_KEY);
				return new JsonResult(HttpStatus.OK, "登录成功", token);
			}
		}
		return new JsonResult(HttpStatus.ACCEPTED, "账号或密码错误");
	}

	/**
	 * 用户短信验证码登录
	 * 
	 * @param userName
	 * @param code
	 * @return
	 */
	@Override
	public JsonResult signInCode(String userName, String code) {
		return null;
	}

}
