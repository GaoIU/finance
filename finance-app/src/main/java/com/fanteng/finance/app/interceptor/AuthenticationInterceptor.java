package com.fanteng.finance.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fanteng.core.HttpStatus;
import com.fanteng.exception.UnauthorizedException;
import com.fanteng.util.StringUtil;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userAgent = request.getHeader("User-Agent");
		String token = request.getHeader("Authorization");

		if (StringUtil.isBlank(token)) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
		}
		if (StringUtil.isClient(userAgent)) {
			String clientId = request.getHeader("Client-UUID");
			if (StringUtil.isBlank(clientId)) {
				throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
			}

			
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
