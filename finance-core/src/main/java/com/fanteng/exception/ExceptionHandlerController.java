package com.fanteng.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handlerException(Exception e) {
		logger.info(e.getMessage());

		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", 10086);
		map.put("msg", "服务异常，请联系客服人员处理");

		return map;
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handlerBindException(MethodArgumentNotValidException be) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		String message = be.getBindingResult().getFieldError().getDefaultMessage();
		map.put("code", com.fanteng.core.HttpStatus.BAD_REQUEST);
		map.put("msg", message);

		return map;
	}

	@ExceptionHandler(value = ParamErrorException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handlerParamErrorException(ParamErrorException pee) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", pee.getCode());
		map.put("msg", pee.getMsg());

		return map;
	}

	@ExceptionHandler(value = ResourceErrorException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handlerResourceErrorException(ResourceErrorException ree) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("code", ree.getCode());
		map.put("msg", ree.getMsg());

		return map;
	}

}
