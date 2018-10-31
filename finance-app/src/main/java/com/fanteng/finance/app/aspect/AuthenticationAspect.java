package com.fanteng.finance.app.aspect;

import javax.servlet.http.HttpServletRequest;

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
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.util.RSAUtil;

@Aspect
@Component
public class AuthenticationAspect {

	private static final int TOKEN_EXPIRE_TIME = 604800;

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

			String clientId = request.getHeader(UserInfo.AUTHENTICATION_HEADER);
			String value = RSAUtil.matchesByPrivateKey(jsonResult.getData().toString(),
					SignatureProperties.SERVER_PRIVATE_KEY);

			redisClient.setex(clientId, TOKEN_EXPIRE_TIME, value);
			redisClient.setex(value, TOKEN_EXPIRE_TIME, jsonResult.getData().toString());
		}

		return jsonResult;
	}

}