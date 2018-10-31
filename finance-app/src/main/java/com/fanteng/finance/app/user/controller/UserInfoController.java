package com.fanteng.finance.app.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.app.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping
	public JsonResult register(@Valid @RequestBody UserInfo userInfo) {
		return userInfoService.register(userInfo);
	}

}
