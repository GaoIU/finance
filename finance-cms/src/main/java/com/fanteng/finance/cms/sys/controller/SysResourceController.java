package com.fanteng.finance.cms.sys.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.fanteng.finance.entity.SysResource;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/sysResource")
public class SysResourceController {

	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 添加后台资源
	 * 
	 * @param sysResource
	 * @return
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysResource sysResource) {
		String code = sysResource.getCode().toUpperCase();
		boolean checkCode = sysResourceService.checkCode(code, sysResource.getId());
		if (checkCode) {
			return new JsonResult(com.fanteng.core.HttpStatus.BAD_REQUEST, "该资源编码已被使用");
		}

		String method = sysResource.getMethod().toUpperCase();
		sysResource.setCode(code);
		sysResource.setMethod(method);

		Serializable id = sysResourceService.save(sysResource);
		if (id != null) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

	/**
	 * 跳转至后台资源列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/sys/resource/list");
		return mav;
	}

	/**
	 * 获取后台资源列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return sysResourceService.queryList(params);
	}

	/**
	 * 跳转至后台资源新增或修改页面
	 */
	@GetMapping("/gotoInfo")
	public ModelAndView gotoInfo(String id) {
		ModelAndView mav = new ModelAndView("/sys/resource/info");
		if (StringUtil.isNotBlank(id)) {
			SysResource sysResource = sysResourceService.get(id);
			if (sysResource == null) {
				throw new ParamErrorException("非法请求");
			}

			Map<String, Object> info = BeanUtil.toMap(sysResource);
			String parentName = "";
			SysResource one = sysResourceService.findOne("id", Operation.EQ, sysResource.getParentId(), "name");
			if (one != null) {
				parentName = one.getName();
			}
			info.put("parentName", parentName);

			mav.addObject("info", info);
		}
		return mav;
	}

	/**
	 * 查看信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/view")
	public JsonResult view() {
		List<Condition> conditions = new ArrayList<Condition>(0);
		Condition status = new Condition("status", Operation.EQ, SysResource.status_normal);
		Condition type = new Condition("type", Operation.EQ, SysResource.type_menu);
		Condition parentId = new Condition("parentId", Operation.IS_NULL, null);
		Condition sort = new Condition("sort", Operation.ASC, "sort");
		Condition createTime = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(status);
		conditions.add(type);
		conditions.add(parentId);
		conditions.add(sort);
		conditions.add(createTime);

		List<SysResource> list = sysResourceService.findAll(conditions);
		List<Object> menu = sysResourceService.getMenu(list, "children");

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", menu);
	}

}
