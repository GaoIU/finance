package com.fanteng.finance.cms.statistics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.statistics.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	/**
	 * 跳转至充值统计页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoRecharge")
	public ModelAndView gotoRecharge() {
		return new ModelAndView("/statistics/recharge/list");
	}

	/**
	 * 充值统计
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/recharge")
	public JsonResult recharge(@RequestParam Map<String, Object> params) {
		return statisticsService.recharge(params);
	}

}
