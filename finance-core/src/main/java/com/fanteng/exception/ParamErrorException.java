package com.fanteng.exception;

import com.fanteng.core.HttpStatus;

public class ParamErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6884319709544373524L;
	
	private int code;
	
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ParamErrorException() {
		super();
		this.code = HttpStatus.BAD_REQUEST;
	}

	public ParamErrorException(String msg) {
		super(msg);
		this.msg = msg;
		this.code = HttpStatus.BAD_REQUEST;
	}

	public ParamErrorException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

}
