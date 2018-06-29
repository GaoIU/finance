package com.fanteng.finance.quartz.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.finance.entity.ScheduleJob;
import com.fanteng.finance.quartz.service.ScheduleJobService;

@RestController
@RequestMapping("/scheduleJob")
public class ScheduleJobController {

	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 获取定时任务列表
	 * 
	 * @param param
	 * @return
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam(required = false) Map<String, Object> param) {
		return scheduleJobService.queryList(param);
	}

	@PostMapping
	public JsonResult addJob(@Valid @RequestBody ScheduleJob scheduleJob) throws Exception {
		scheduleJobService.saveJob(scheduleJob);
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

}
