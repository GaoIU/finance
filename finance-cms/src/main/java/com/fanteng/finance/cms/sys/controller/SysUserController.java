package com.fanteng.finance.cms.sys.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.service.SysUserService;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody Map<String, Object> param) throws Exception {
		return sysUserService.register(param);
	}

}
