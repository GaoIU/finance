package com.fanteng.finance.cms.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.entity.SysUser;

@RestController
public class HomeController {

	@Value("${sys.user.default.session.key}")
	private String default_session_key;

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

	/**
	 * 跳转至后台首页
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		SysUser sysUser = (SysUser) session.getAttribute(default_session_key);
		if (sysUser == null) {
			mav.setViewName("/sys/login");
			return mav;
		}

		mav.setViewName("/sys/index");

		return mav;
	}

	@PostMapping("/index")
	public JsonResult getMenu() {
		return null;
	}

	/**
	 * session过期
	 * 
	 * @return
	 */
	@GetMapping("/signOut")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Map<String, Object> signOut() {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", com.fanteng.core.HttpStatus.UNAUTHORIZED);
		map.put("msg", "用户登录已过期，请重新登录");
		return map;
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
