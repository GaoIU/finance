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
	private Integer total = 0;

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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public Page(Integer current, Integer size, Integer total, List<?> list) {
		super();
		this.current = current;
		this.size = size;
		this.total = total;
		this.list = list;
	}

}
