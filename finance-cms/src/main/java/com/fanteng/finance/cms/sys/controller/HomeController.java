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

	@GetMapping("/403")
	public ModelAndView forbidden() {
		ModelAndView mav = new ModelAndView("/common/403");
		return mav;
	}

	@GetMapping("/404")
	public ModelAndView notFound() {
		ModelAndView mav = new ModelAndView("/common/404");
		return mav;
	}

	@GetMapping("/500")
	public ModelAndView internalServerError() {
		ModelAndView mav = new ModelAndView("/common/500");
		return mav;
	}

}
