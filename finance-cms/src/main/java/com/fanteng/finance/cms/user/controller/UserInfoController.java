package com.fanteng.finance.cms.user.controller;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.user.service.LogUserAccountService;
import com.fanteng.finance.cms.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.EncryptUtil;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private LogUserAccountService logUserAccountService;

	/**
	 * 跳转至用户列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		return new ModelAndView("/user/info/list");
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

	@PutMapping
	public JsonResult resetPwd(String id) {
		if (StringUtil.isBlank(id)) {
			throw new ParamErrorException("无效参数");
		}

		UserInfo userInfo = userInfoService.get(id);
		if (userInfo == null) {
			throw new ParamErrorException("无效参数");
		}

		userInfo.setPassword(EncryptUtil.encodeByBC("123456"));
		if (userInfoService.update(userInfo)) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 启用或者禁用用户
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/usable")
	public JsonResult usable(@RequestBody Map<String, Object> param) throws Exception {
		String id = MapUtils.getString(param, "id");
		Short status = MapUtils.getShort(param, "status");
		if (StringUtil.isBlank(id) || status == null) {
			throw new ParamErrorException("无效参数");
		}

		boolean usable = userInfoService.usable(id, status);
		if (usable) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", usable);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping
	public JsonResult del(String id) {
		if (StringUtil.isBlank(id)) {
			throw new ParamErrorException("无效参数");
		}
		String[] ids = id.split(",");
		if (ArrayUtils.isEmpty(ids)) {
			throw new ParamErrorException("无效参数");
		}

		for (String userInfoId : ids) {
			userInfoService.delete(userInfoId);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
	}

	/**
	 * 跳转至用户账户明细页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoLogUserAccountList")
	public ModelAndView gotoLogUserAccountList() {
		return new ModelAndView("/user/logAccount/list");
	}

	/**
	 * 查询用户账户明细列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/queryLogUserAccount")
	public JsonResult queryLogUserAccount(@RequestParam Map<String, Object> params) throws Exception {
		return logUserAccountService.queryList(params);
	}

}
