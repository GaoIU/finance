package com.fanteng.finance.quartz.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.entity.ScheduleJob;

@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
		try {
			TaskUtil.invokeMethod(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParamErrorException("配置错误");
		}
	}

}
