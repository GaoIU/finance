package com.fanteng.finance.app.user.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;

@RestController
@RequestMapping("/signIn")
public class AuthenticationController {

	@PostMapping
	public JsonResult signIn(@RequestBody Map<String, Object> params) {
		return null;
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
