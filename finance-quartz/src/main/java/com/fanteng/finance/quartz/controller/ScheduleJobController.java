package com.fanteng.finance.quartz.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.entity.ScheduleJob;
import com.fanteng.finance.quartz.service.ScheduleJobService;
import com.fanteng.util.StringUtil;

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

	/**
	 * 新增一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult addJob(@Valid @RequestBody ScheduleJob scheduleJob) throws Exception {
		scheduleJobService.saveJob(scheduleJob);
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 修改一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	@PutMapping
	public JsonResult editJob(@Valid @RequestBody ScheduleJob scheduleJob) throws Exception {
		scheduleJobService.updateJob(scheduleJob);
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 删除一个定时任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public JsonResult delJob(@PathVariable String id) throws Exception {
		scheduleJobService.delJob(id);
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 暂停或者恢复一个定时任务
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/pauseJob")
	public JsonResult pauseJob(@RequestBody Map<String, Object> param) throws Exception {
		String id = MapUtils.getString(param, "id");
		Short status = MapUtils.getShort(param, "status");
		if (StringUtil.isBlank(id) || status == null) {
			throw new ParamErrorException("非法参数");
		}

		if (status == ScheduleJob.status_pause) {
			scheduleJobService.pauseJob(id);
		}

		if (status == ScheduleJob.status_normal) {
			scheduleJobService.resumeJob(id);
		}
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 是否并发
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/concurrentJob")
	public JsonResult concurrentJob(@RequestBody Map<String, Object> param) throws Exception {
		String id = MapUtils.getString(param, "id");
		Short concurrent = MapUtils.getShort(param, "concurrent");
		if (StringUtil.isBlank(id) || concurrent == null) {
			throw new ParamErrorException("非法参数");
		}

		scheduleJobService.concurrent(id, concurrent);
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 关闭所有定时任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/shutdowns")
	public JsonResult shutdowns() throws Exception {
		scheduleJobService.shutdowns();
		return new JsonResult(HttpStatus.OK, "操作成功");
	}

}
