package com.fanteng.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fanteng.core.JsonResult;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = ParamErrorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public JsonResult handlerParamErrorException(ParamErrorException pee) {
		return null;
	}

}
