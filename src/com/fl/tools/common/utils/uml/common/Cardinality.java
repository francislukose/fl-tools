package com.fl.tools.common.utils.uml.common;

public enum Cardinality {
	ONE("[1]"), MANY("[0..*]");

	Cardinality(String n) {
		this.label = n;
	}

	public String label;
}
