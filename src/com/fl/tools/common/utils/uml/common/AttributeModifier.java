package com.fl.tools.common.utils.uml.common;

public enum AttributeModifier {
	PUBLIC("+"), PRIVATE("-"), PROTECTED("#"), PACKAGE("~"), STATIC("{static}"), ABSTRACT("{abstract}");

	private AttributeModifier(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
