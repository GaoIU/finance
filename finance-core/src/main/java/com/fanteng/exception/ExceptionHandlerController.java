package com.fanteng.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handlerException() {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", 10086);
		map.put("msg", "服务异常，请联系客服人员处理");

		return map;
	}

	@ExceptionHandler(value = ParamErrorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handlerParamErrorException(ParamErrorException pee) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", pee.getCode());
		map.put("msg", pee.getMsg());

		return map;
	}

}
