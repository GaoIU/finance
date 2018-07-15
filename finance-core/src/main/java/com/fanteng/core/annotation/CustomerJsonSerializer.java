package com.fanteng.core.annotation;

import com.fanteng.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerJsonSerializer {

	ObjectMapper mapper = new ObjectMapper();
	JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

	public void filter(Class<?> clazz, String include, String filter) {
		if (clazz == null)
			return;
		if (StringUtil.isNotBlank(include)) {
			jacksonFilter.include(clazz, include.split(","));
		}
		if (StringUtil.isNotBlank(filter)) {
			jacksonFilter.filter(clazz, filter.split(","));
		}
		mapper.addMixIn(clazz, jacksonFilter.getClass());
	}

	public String toJson(Object object) throws JsonProcessingException {
		mapper.setFilterProvider(jacksonFilter);
		return mapper.writeValueAsString(object);
	}

	public void filter(JSON json) {
		this.filter(json.type(), json.include(), json.filter());
	}

}
