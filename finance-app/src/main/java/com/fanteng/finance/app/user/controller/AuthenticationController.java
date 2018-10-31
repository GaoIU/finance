package com.fanteng.finance.app.user.controller;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.app.user.service.UserInfoService;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/signIn")
public class AuthenticationController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 用户账号密码登录
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping
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

	@PostMapping("/code")
	public JsonResult signInCode(@RequestBody Map<String, Object> params) {
		return null;
	}

	@PostMapping("/token")
	public JsonResult signInToken() {
		return null;
	}

}
