package com.fanteng.exception;

import com.fanteng.core.HttpStatus;

public class ResourceErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -930150266042060622L;

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

	public ResourceErrorException() {
		super();
		this.code = HttpStatus.NOT_FOUND;
	}

	public ResourceErrorException(String msg) {
		super(msg);
		this.msg = msg;
		this.code = HttpStatus.NOT_FOUND;
	}

	public ResourceErrorException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

}
