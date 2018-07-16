package com.fanteng.finance.cms.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;

@RestController
public class AuthenticationController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${sys.user.default.session.key}")
	private String default_session_key;

	/**
	 * 后台用户登录
	 * 
	 * @param session
	 * @param param
	 * @return
	 */
	@PostMapping("/signIn")
	public JsonResult signIn(HttpSession session, @RequestBody Map<String, Object> param) {
		JsonResult jsonResult = sysUserService.signIn(param);
		if (jsonResult.getCode() != com.fanteng.core.HttpStatus.OK) {
			return jsonResult;
		}

		String userName = MapUtils.getString(param, "userName");
		String password = MapUtils.getString(param, "password");
		SysUser sysUser = (SysUser) jsonResult.getData();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
				password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		session.setAttribute(default_session_key, sysUser);

		jsonResult.setData("/index");
		return jsonResult;
	}

}
