package com.fanteng.finance.cms.controller;

import java.util.Map;

import javax.validation.Valid;

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

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.service.SysConfigService;
import com.fanteng.finance.entity.SysConfig;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 跳转至后台系统配置列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		return new ModelAndView("/sys/config/list");
	}

	/**
	 * 获取后台系统配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return sysConfigService.queryList(params);
	}

	/**
	 * 跳转至后台系统配置新增或修改页面
	 */
	@GetMapping("/gotoInfo")
	public ModelAndView gotoInfo(String id) {
		ModelAndView mav = new ModelAndView("/sys/config/info");
		if (StringUtil.isNotBlank(id)) {
			SysConfig sysConfig = sysConfigService.get(id);
			if (sysConfig == null) {
				throw new ParamErrorException("非法请求");
			}

			mav.addObject("info", sysConfig);
		}
		return mav;
	}

	/**
	 * 验证后台系统配置编码是否存在
	 * 
	 * @param code
	 * @param sysConfigId
	 * @return
	 */
	@PostMapping("/checkCode")
	public JsonResult checkCode(String code, String sysConfigId) {
		code = code.toUpperCase();
		boolean checkCode = sysConfigService.checkCode(code, sysConfigId);
		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", checkCode);
	}

	/**
	 * 添加后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysConfig sysConfig) {
		boolean checkCode = sysConfigService.checkCode(sysConfig.getCode(), sysConfig.getId());
		if (checkCode) {
			return new JsonResult(com.fanteng.core.HttpStatus.BAD_REQUEST, "该配置编码已被使用");
		}

		return sysConfigService.register(sysConfig);
	}

	/**
	 * 修改后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	@PutMapping
	public JsonResult edit(@Valid @RequestBody SysConfig sysConfig) {
		boolean checkCode = sysConfigService.checkCode(sysConfig.getCode(), sysConfig.getId());
		if (checkCode) {
			return new JsonResult(com.fanteng.core.HttpStatus.BAD_REQUEST, "该配置编码已被使用");
		}

		return sysConfigService.edit(sysConfig);
	}

	/**
	 * 批量删除后台系统配置
	 * 
	 * @param ids
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

		sysConfigService.del(ids);

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
	}

	/**
	 * 启用或者禁用后台系统配置
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

		SysConfig sysConfig = sysConfigService.get(id);
		sysConfig.setStatus(status);
		return sysConfigService.edit(sysConfig);
	}

}
