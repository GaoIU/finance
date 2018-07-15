package com.fanteng.finance.cms.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

	/**
	 * 跳转至登录页面
	 * 
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("/sys/login");
		return mav;
	}

}
