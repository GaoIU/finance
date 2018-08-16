package com.fanteng.exception;

import com.fanteng.core.HttpStatus;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1952673156267507824L;

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

	public UnauthorizedException() {
		super();
		this.code = HttpStatus.UNAUTHORIZED;
	}

	public UnauthorizedException(String msg) {
		super(msg);
		this.code = HttpStatus.UNAUTHORIZED;
		this.msg = msg;
	}

	public UnauthorizedException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

}
