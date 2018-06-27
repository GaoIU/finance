package com.fanteng.core;

public class Condition {

	private String property;

	private Operation operation;

	private Object value;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Condition() {
		super();
	}

	public Condition(String property, Operation operation, Object value) {
		super();
		this.property = property;
		this.operation = operation;
		this.value = value;
	}

}
