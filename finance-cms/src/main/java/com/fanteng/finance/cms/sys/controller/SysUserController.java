package com.fanteng.finance.cms.sys.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Value("${sys.user.default.session.key}")
	private String default_session_key;

	@GetMapping
	public JsonResult queryList(@RequestParam SysUser sysUser) {
		return sysUserService.queryList();
	}

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysUser sysUser) throws Exception {
		return sysUserService.register(sysUser);
	}

}
