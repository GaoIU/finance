package com.fanteng.finance.cms.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.user.service.UserInfoService;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 跳转至用户列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/user/info/list");
		return mav;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return userInfoService.queryList(params);
	}

}
