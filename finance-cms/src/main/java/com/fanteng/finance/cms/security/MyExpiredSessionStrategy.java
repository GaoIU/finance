package com.fanteng.finance.cms.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.fanteng.core.HttpStatus;
import com.fanteng.util.JsonUtil;

public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", HttpStatus.UNAUTHORIZED);
		map.put("msg", "该账户已在别处登录");
		String json = JsonUtil.toJson(map);

		HttpServletResponse response = event.getResponse();
		response.setStatus(HttpStatus.UNAUTHORIZED);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json);
	}

}
