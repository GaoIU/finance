package com.fanteng.core;

import java.util.List;

public class Page {

	/**
	 * 起始页，默认：1
	 */
	private Integer current = 1;

	/**
	 * 每页条数，默认：15
	 */
	private Integer size = 15;

	/**
	 * 总条数，默认：0
	 */
	private Integer totle = 0;

	/**
	 * 数据
	 */
	private List<?> list;

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotle() {
		return totle;
	}

	public void setTotle(Integer totle) {
		this.totle = totle;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Page() {
		super();
	}

	public Page(Integer current, Integer size, Integer totle, List<?> list) {
		super();
		this.current = current;
		this.size = size;
		this.totle = totle;
		this.list = list;
	}

}
