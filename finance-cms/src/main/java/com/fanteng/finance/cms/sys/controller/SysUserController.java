package com.fanteng.finance.cms.sys.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.exception.CustomException;
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
	public JsonResult queryList() {
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

	/**
	 * 上传头像
	 * 
	 * @param session
	 * @param avatar
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public JsonResult uploadAvatar(HttpSession session, MultipartFile avatar, String sysUserId) throws Exception {
		JsonResult jsonResult = sysUserService.uploadAvatar(avatar, sysUserId);
		if (jsonResult.getCode() == com.fanteng.core.HttpStatus.OK) {
			SysUser sysUser = (SysUser) jsonResult.getData();
			session.setAttribute(default_session_key, sysUser);
			jsonResult.setData(sysUser.getAvatar());
			return jsonResult;
		}
		throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "上传文件失败");
	}

}
