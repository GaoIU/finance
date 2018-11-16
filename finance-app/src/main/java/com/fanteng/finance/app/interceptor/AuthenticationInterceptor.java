package com.fanteng.finance.app.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.RedisClient;
import com.fanteng.exception.UnauthorizedException;
import com.fanteng.finance.app.util.CommonUtil;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.JsonUtil;
import com.fanteng.util.RSAUtil;
import com.fanteng.util.SignatureProperties;
import com.fanteng.util.StringUtil;

public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final String[] EXCLUDE_FITER_URL = { "/signIn", "/register" };

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Autowired
	private RedisClient redisClient;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userAgent = request.getHeader("User-Agent");
		String clientId = null;
		if (CommonUtil.isClient(userAgent)) {
			clientId = request.getHeader(UserInfo.AUTHENTICATION_HEADER);
			if (StringUtil.isBlank(clientId)) {
				throw new UnauthorizedException(HttpStatus.NOT_ACCEPTABLE, "你怕是失了智");
			}
		}

		String uri = request.getRequestURI();
		for (String URL : EXCLUDE_FITER_URL) {
			if (antPathMatcher.match(uri, URL)) {
				return true;
			}
		}

		String token = request.getHeader("Authorization");
		if (StringUtil.isBlank(token)) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
		}

		String userId;
		Map<?, ?> map;
		try {
			String json = RSAUtil.matchesByPrivateKey(token, SignatureProperties.SERVER_PRIVATE_KEY);
			map = JsonUtil.fromJson(json, Map.class);
			userId = MapUtils.getString(map, "id");
		} catch (Exception e) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
		}

		if (CommonUtil.isClient(userAgent)) {
			userId = redisClient.get(clientId);
			if (StringUtil.isBlank(userId)) {
				throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
			}
		}

		String tokenOfRedis = redisClient.get(userId);
		if (StringUtil.isBlank(tokenOfRedis)) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "我已经不记得你是谁了，请重新登录");
		}

		Map<?, ?> mapOfRedis;
		try {
			String jsonOfRedis = RSAUtil.matchesByPrivateKey(tokenOfRedis, SignatureProperties.SERVER_PRIVATE_KEY);
			mapOfRedis = JsonUtil.fromJson(jsonOfRedis, Map.class);
		} catch (Exception e) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "来者何人，报上名来");
		}

		if (MapUtils.getShort(mapOfRedis, "status") == UserInfo.STATUS_DISABLE) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "该账号已被禁用，请联系客服人员");
		}

		if (!StringUtil.equals(token, tokenOfRedis)) {
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "该账号已在别处登录，请重新登录");
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
