package com.fanteng.finance.app.aspect;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.RedisClient;
import com.fanteng.finance.app.properties.SignatureProperties;
import com.fanteng.finance.app.util.CommonUtil;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.JsonUtil;
import com.fanteng.util.RSAUtil;

@Aspect
@Component
public class AuthenticationAspect {

	private static final int APP_TOKEN_EXPIRE_TIME = 604800;

	private static final int PC_TOKEN_EXPIRE_TIME = 3600;

	@Autowired
	private RedisClient redisClient;

	@Pointcut("execution(* com.fanteng.finance.app.user.service.impl.UserInfoServiceImpl.signIn*(..))")
	public void signInPoinCut() {
	}

	@Around("signInPoinCut()")
	public JsonResult signInAround(ProceedingJoinPoint joinPoint) throws Throwable {
		JsonResult jsonResult = (JsonResult) joinPoint.proceed();
		if (jsonResult.getCode() == HttpStatus.OK) {
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = sra.getRequest();

			String value = RSAUtil.matchesByPrivateKey(jsonResult.getData().toString(),
					SignatureProperties.SERVER_PRIVATE_KEY);
			Map<?, ?> map = JsonUtil.fromJson(value, Map.class);

			String userAgent = request.getHeader("User-Agent");
			if (CommonUtil.isClient(userAgent)) {
				String clientId = request.getHeader(UserInfo.AUTHENTICATION_HEADER);
				redisClient.setex(clientId, APP_TOKEN_EXPIRE_TIME, MapUtils.getString(map, "id"));
				redisClient.setex(MapUtils.getString(map, "id"), APP_TOKEN_EXPIRE_TIME,
						jsonResult.getData().toString());
			} else {
				redisClient.setex(MapUtils.getString(map, "id"), PC_TOKEN_EXPIRE_TIME, jsonResult.getData().toString());
			}

		}

		return jsonResult;
	}

}
