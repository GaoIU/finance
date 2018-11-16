package com.fanteng.finance.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.Condition;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 跳转至后台用户列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/sys/user/list");
		return mav;
	}

	/**
	 * 获取后台用户列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return sysUserService.queryList(params);
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
	 * 修改后台用户
	 * 
	 * @param sysUser
	 * @param session
	 * @return
	 */
	@PutMapping
	public JsonResult edit(@Valid @RequestBody SysUser sysUser) {
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

		return sysUserService.edit(sysUser);
	}

	/**
	 * 删除后台用户
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

		return sysUserService.del(ids);
	}

	/**
	 * 跳转至后台用户新增或修改页面
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/gotoInfo")
	public ModelAndView gotoInfo(String id) {
		ModelAndView mav = new ModelAndView("/sys/user/info");

		List<Condition> conditions = new ArrayList<>(0);
		Condition status = new Condition("status", Operation.EQ, SysRole.STATUS_NORMAL);
		conditions.add(status);
		List<SysRole> sysRoles = sysRoleService.findAll(conditions);
		mav.addObject("sysRoles", getSysRoles(sysRoles, null));

		if (StringUtil.isNotBlank(id)) {
			SysUser sysUser = sysUserService.get(id);
			if (sysUser == null) {
				throw new ParamErrorException("非法请求");
			}

			List<SysRole> list = sysRoleService.getSysRolesBySysUserId(id);
			mav.addObject("sysRoles", getSysRoles(sysRoles, list));

			mav.addObject("info", sysUser);
		}
		return mav;
	}

	/**
	 * 获取用户所属角色
	 * 
	 * @param sysRoles
	 * @param list
	 * @return
	 */
	private List<Object> getSysRoles(List<SysRole> sysRoles, List<SysRole> list) {
		List<Object> roles = new ArrayList<>(0);
		for (SysRole sysRole : sysRoles) {
			String id = sysRole.getId();
			Map<String, Object> map = BeanUtil.toMap(sysRole);
			map.put("checked", false);
			if (CollectionUtils.isNotEmpty(list)) {
				for (SysRole role : list) {
					if (StringUtil.equals(id, role.getId())) {
						map.put("checked", true);
					}
				}
			}

			roles.add(map);
		}

		return roles;
	}

	/**
	 * 启用或者禁用后台角色
	 * 
	 * @param param
	 * @return
	 */
	@PutMapping("/usable")
	public JsonResult usable(@RequestBody Map<String, Object> param) {
		String id = MapUtils.getString(param, "id");
		Short status = MapUtils.getShort(param, "status");
		if (StringUtil.isBlank(id) || status == null) {
			throw new ParamErrorException("无效参数");
		}

		boolean usable = sysUserService.usable(id, status);
		if (usable) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", usable);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

}
