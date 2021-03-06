package com.fanteng.finance.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.CustomException;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.exception.UnauthorizedException;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysUser;

@RestController
public class HomeController {

	@Value("${sys.user.default.session.key}")
	private String defaultSessionKey;

	@Autowired
	private SysResourceService sysResourceService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 跳转至登录页面
	 * 
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("/sys/login");
	}

	/**
	 * 跳转至后台首页
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("/sys/index");
	}

	/**
	 * 获取菜单
	 * 
	 * @param session
	 * @return
	 */
	@PostMapping("/index")
	public JsonResult getMenu(HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		if (sysUser == null) {
			throw new UnauthorizedException("登录已过期，请重新登录");
		}

		List<SysResource> list = sysResourceService.getResource(sysUser.getId());
		List<Object> menu = sysResourceService.getMenu(list, sysUser.getId());
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", menu);
	}

	/**
	 * 跳转至个人信息页面
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/sysUserInfo")
	public ModelAndView sysUserInfo(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		if (sysUser == null) {
			throw new UnauthorizedException("登录已过期，请重新登录");
		}

		List<SysRole> list = sysRoleService.getSysRolesBySysUserId(sysUser.getId());
		mav.addObject("sysUser", sysUserService.get(sysUser.getId()));
		mav.addObject("roles", list);
		mav.setViewName("/sys/user/userinfo");

		return mav;
	}

	/**
	 * 修改个人信息
	 * 
	 * @param sysUser
	 * @return
	 */
	@PutMapping("/sysUserInfo")
	public JsonResult changeSysUser(HttpSession session, @Valid @RequestBody SysUser sysUser) {
		String id = sysUser.getId();
		boolean checkUserName = sysUserService.checkUserName(sysUser.getUserName(), id);
		if (checkUserName) {
			throw new ParamErrorException("该账号已被使用");
		}
		boolean checkNickName = sysUserService.checkNickName(sysUser.getNickName(), id);
		if (checkNickName) {
			throw new ParamErrorException("该昵称已被使用");
		}
		boolean checkMobile = sysUserService.checkMobile(sysUser.getMobile(), id);
		if (checkMobile) {
			throw new ParamErrorException("该手机号已存在");
		}

		boolean updateIgnore = sysUserService.updateIgnore(sysUser);
		if (updateIgnore) {
			sysUser = sysUserService.get(sysUser.getId());
			session.setAttribute(defaultSessionKey, sysUser);
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", updateIgnore);
		}
		throw new CustomException(com.fanteng.core.HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
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
			session.setAttribute(defaultSessionKey, sysUser);
			jsonResult.setData(sysUser.getAvatar());
			return jsonResult;
		}
		throw new CustomException(com.fanteng.core.HttpStatus.INTERNAL_SERVER_ERROR, "上传文件失败");
	}

	/**
	 * 跳转至修改密码页面
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/resetPwd")
	public ModelAndView resetPwd(HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		if (sysUser == null) {
			throw new UnauthorizedException("登录已过期，请重新登录");
		}

		ModelAndView mav = new ModelAndView("/sys/user/resetPwd");
		return mav;
	}

	/**
	 * 修改密码
	 * 
	 * @param session
	 * @param password
	 * @param oldPwd
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/resetPwd")
	public JsonResult changePwd(HttpSession session, String password, String oldPwd) throws Exception {
		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		if (sysUser == null) {
			throw new UnauthorizedException("登录已过期，请重新登录");
		}

		return sysUserService.changePwd(sysUser, password, oldPwd);
	}

	/**
	 * session过期
	 * 
	 * @return
	 */
	@GetMapping("/signOut")
	public ModelAndView signOut() {
		ModelAndView mav = new ModelAndView("/common/401");
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
