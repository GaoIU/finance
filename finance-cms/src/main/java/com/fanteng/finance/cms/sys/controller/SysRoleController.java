package com.fanteng.finance.cms.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.Condition;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
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
	 * 跳转至后台角色列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/sys/role/list");
		return mav;
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

	@PostMapping("/getPermission")
	public JsonResult getPermission(String sysRoleId) {
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.STATUS_NORMAL);
		Condition type = new Condition("type", Operation.EQ, SysResource.TYPE_MENU);
		Condition parentId = new Condition("parentId", Operation.IS_NULL, null);
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(status);
		conditions.add(type);
		conditions.add(parentId);
		conditions.add(sort);
		conditions.add(createTime);

		List<SysResource> list = sysResourceService.findAll(conditions);
		List<Object> permission = sysResourceService.getPermission(list);
		List<String> ids = new ArrayList<>(0);
		if (StringUtil.isNotBlank(sysRoleId)) {
			ids = sysResourceService.getIdsBySysRoleId(sysRoleId);
		}

		Map<String, Object> data = new HashMap<>(0);
		data.put("permission", permission);
		data.put("ids", ids);

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", data);
	}

}
