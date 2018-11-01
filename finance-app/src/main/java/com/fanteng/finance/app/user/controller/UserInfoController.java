package com.fanteng.finance.app.user.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@PutMapping
	public String hello() {
		return "hello word!";
	}

}
