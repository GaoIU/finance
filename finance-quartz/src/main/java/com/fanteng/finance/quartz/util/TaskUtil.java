package com.fanteng.finance.quartz.util;

import java.lang.reflect.Method;

import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.entity.ScheduleJob;
import com.fanteng.util.StringUtil;

public class TaskUtil {

	public static void invokeMethod(ScheduleJob scheduleJob) throws Exception {
		Object object = null;
		Class<?> clazz = null;

		if (StringUtil.isNotBlank(scheduleJob.getBeanClass())) {
			clazz = Class.forName(scheduleJob.getBeanClass());
			object = clazz.newInstance();
		}

		if (object == null) {
			throw new ParamErrorException("配置错误");
		}
		clazz = object.getClass();
		Method method = null;
		method = clazz.getDeclaredMethod(scheduleJob.getMethodName());

		if (method == null) {
			throw new ParamErrorException("配置错误");
		}

		method.invoke(object);
	}

}
