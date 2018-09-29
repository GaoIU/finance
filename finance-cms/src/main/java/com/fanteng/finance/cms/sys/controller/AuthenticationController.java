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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.UnauthorizedException;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.util.EncryptUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysResourceService sysResourceService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${sys.user.default.session.key}")
	private String defaultSessionKey;

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
		session.setAttribute(defaultSessionKey, sysUser);

		jsonResult.setData("index");
		return jsonResult;
	}

	/**
	 * 验证密码是否一致
	 * 
	 * @param password
	 * @return
	 */
	@PostMapping("/checkPassword")
	public JsonResult checkPassword(HttpSession session, @RequestParam(required = false) String password) {
		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		if (sysUser == null) {
			throw new UnauthorizedException(com.fanteng.core.HttpStatus.UNAUTHORIZED, "登录已过期，请重新登录");
		}
		boolean checkPassword = EncryptUtil.matchesByBC(password, sysUser.getPassword());
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkPassword);
	}

	/**
	 * 验证手机是否存在
	 * 
	 * @param mobile
	 * @return
	 */
	@PostMapping("/checkMobile")
	public JsonResult checkMobile(String mobile, String sysUserId) {
		boolean checkMobile = sysUserService.checkMobile(mobile, sysUserId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkMobile);
	}

	/**
	 * 验证昵称是否存在
	 * 
	 * @param nickName
	 * @return
	 */
	@PostMapping("/checkNickName")
	public JsonResult checkNickName(String nickName, String sysUserId) {
		boolean checkNickName = sysUserService.checkNickName(nickName, sysUserId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkNickName);
	}

	/**
	 * 验证账号是否存在
	 * 
	 * @param userName
	 * @return
	 */
	@PostMapping("/checkUserName")
	public JsonResult checkUserName(String userName, String sysUserId) {
		boolean checkUserName = sysUserService.checkUserName(userName, sysUserId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkUserName);
	}

	/**
	 * 验证后台资源编码是否存在
	 * 
	 * @param code
	 * @return
	 */
	@PostMapping("/checkCode")
	public JsonResult checkCode(String code, String sysResourceId) {
		code = code.toUpperCase();
		boolean checkCode = sysResourceService.checkCode(code, sysResourceId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkCode);
	}

}
