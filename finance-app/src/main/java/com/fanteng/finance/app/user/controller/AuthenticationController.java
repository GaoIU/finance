package com.fanteng.finance.app.user.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.app.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.StringUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 用户注册
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/register")
	public JsonResult register(@Valid @RequestBody UserInfo userInfo) throws Exception {
		return userInfoService.register(userInfo);
	}

	/**
	 * 用户账号密码登录
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/signIn")
	public JsonResult signIn(@RequestBody Map<String, Object> params) throws Exception {
		String userName = MapUtils.getString(params, "userName");
		String password = MapUtils.getString(params, "password");

		if (StringUtil.isBlank(userName)) {
			throw new ParamErrorException("账号不能为空");
		}
		if (StringUtil.isBlank(password)) {
			throw new ParamErrorException("密码不能为空");
		}

		JsonResult jsonResult = userInfoService.signIn(userName, password);

		return jsonResult;
	}

	@PostMapping("/signInCode")
	public JsonResult signInCode(@RequestBody Map<String, Object> params) {
		return null;
	}

	@PostMapping("/signInToken")
	public JsonResult signInToken() {
		return null;
	}

}
