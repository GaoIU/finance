package com.fanteng.finance.app.user.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	@PostMapping("/hello")
	public Map<String, Object> hello(@RequestBody Map<String, Object> params) {
		return params;
	}

}
