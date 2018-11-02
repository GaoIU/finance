package com.fanteng.finance.cms.order.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.order.service.RechargeOrderService;

@RestController
@RequestMapping("/rechargeOrder")
public class RechargeOrderController {

	@Autowired
	private RechargeOrderService rechargeOrderService;

	/**
	 * 跳转至充值列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/order/recharge/list");
		return mav;
	}

	/**
	 * 获取充值列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return rechargeOrderService.queryList(params);
	}

}
