package com.fanteng.finance.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.fanteng.exception.CustomException;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysResourceService sysResourceService;

	@Value("${sys.role.admin.code}")
	private String ADMINISTRATOR;

	/**
	 * 添加后台角色
	 * 
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysRole sysRole) throws Exception {
		return sysRoleService.register(sysRole);
	}

	/**
	 * 修改后台角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@PutMapping
	public JsonResult edit(@Valid @RequestBody SysRole sysRole) {
		String code = sysRole.getCode().toUpperCase();
		boolean checkCode = sysRoleService.checkCode(code, sysRole.getId());
		if (checkCode) {
			return new JsonResult(com.fanteng.core.HttpStatus.BAD_REQUEST, "该角色编码已被使用");
		}

		if (StringUtil.equals(ADMINISTRATOR, code)) {
			return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "超级管理员不可被修改");
		}

		return sysRoleService.edit(sysRole);
	}

	/**
	 * 删除后台角色
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

		for (String sysRoleId : ids) {
			SysRole sysRole = sysRoleService.get(sysRoleId);
			if (sysRole != null && StringUtil.equals(ADMINISTRATOR, sysRole.getCode())) {
				throw new CustomException(com.fanteng.core.HttpStatus.ACCEPTED, "超级管理员不可被删除");
			}
		}

		return sysRoleService.del(ids);
	}

	/**
	 * 跳转至后台角色列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		return new ModelAndView("/sys/role/list");
	}

	/**
	 * 获取后台角色列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return sysRoleService.queryList(params);
	}

	/**
	 * 跳转至后台角色新增或修改页面
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/gotoInfo")
	public ModelAndView gotoInfo(String id) {
		ModelAndView mav = new ModelAndView("/sys/role/info");
		if (StringUtil.isNotBlank(id)) {
			SysRole sysRole = sysRoleService.get(id);
			if (sysRole == null) {
				throw new ParamErrorException("非法请求");
			}

			if (StringUtil.equals(ADMINISTRATOR, sysRole.getCode())) {
				throw new CustomException(com.fanteng.core.HttpStatus.ACCEPTED, "超级管理员不可被修改");
			}

			mav.addObject("info", sysRole);
		}
		return mav;
	}

	/**
	 * 验证后台角色编码是否存在
	 * 
	 * @param code
	 * @param sysResourceId
	 * @return
	 */
	@PostMapping("/checkCode")
	public JsonResult checkCode(String code, String sysRoleId) {
		code = code.toUpperCase();
		boolean checkCode = sysRoleService.checkCode(code, sysRoleId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkCode);
	}

	/**
	 * 获取角色权限
	 * 
	 * @param sysRoleId
	 * @return
	 */
	@PostMapping("/getPermission")
	public JsonResult getPermission(String sysRoleId) {
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.STATUS_NORMAL);
		Condition type = new Condition("type", Operation.EQ, SysResource.TYPE_MENU);
		Criterion[] criterions = { Restrictions.eq("parentId", ""), Restrictions.isNull("parentId") };
		Condition parentId = new Condition("parentId", Operation.OR, criterions);
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(status);
		conditions.add(type);
		conditions.add(parentId);
		conditions.add(sort);
		conditions.add(createTime);

		List<SysResource> list = sysResourceService.findAll(conditions);
		List<String> ids = new ArrayList<>(0);
		if (StringUtil.isNotBlank(sysRoleId)) {
			ids = sysResourceService.getIdsBySysRoleId(sysRoleId);
		}
		List<Object> permission = sysResourceService.getPermission(list, ids);

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", permission);
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

		SysRole sysRole = sysRoleService.get(id);
		if (sysRole == null) {
			throw new ParamErrorException("非法请求");
		}

		if (StringUtil.equals(ADMINISTRATOR, sysRole.getCode())) {
			throw new CustomException(com.fanteng.core.HttpStatus.ACCEPTED, "超级管理员不可被修改");
		}

		sysRole.setStatus(status);
		boolean usable = sysRoleService.update(sysRole);
		if (usable) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", usable);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

}
