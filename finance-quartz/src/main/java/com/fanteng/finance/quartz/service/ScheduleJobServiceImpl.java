package com.fanteng.finance.quartz.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.dao.ScheduleJobDao;
import com.fanteng.finance.entity.ScheduleJob;
import com.fanteng.finance.service.ScheduleJobService;

@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobDao, ScheduleJob> implements ScheduleJobService {

	@PostConstruct
	public void init() {
		List<ScheduleJob> list = findAll();

		if (CollectionUtils.isNotEmpty(list)) {

		}
	}

}
