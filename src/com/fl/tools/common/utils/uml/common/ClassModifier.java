package com.fl.tools.common.utils.uml.common;

public enum ClassModifier {

	ABSTRACT("abstract"), INTERFACE("interface"), ENUM("enum"), CLASS("class");

	private ClassModifier(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
