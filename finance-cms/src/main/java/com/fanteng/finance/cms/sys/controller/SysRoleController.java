package com.fanteng.finance.cms.sys.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.entity.SysRole;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 添加后台角色
	 * 
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysRole sysRole) throws Exception {
		return sysRoleService.register(sysRole);
	}

	/**
	 * 跳转至后台角色列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/sys/role/list");
		return mav;
	}

	/**
	 * 获取后台角色列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return sysRoleService.queryList(params);
	}

}
