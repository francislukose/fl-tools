package com.fl.tools.common.utils.uml.common;

public enum EntityRelationship {
	ONE_TO_ONE("||--||"), ONE_TO_MANY("|o--|{"), MANY_TO_ONE("}|--o|"), MANY_TO_MANY("}|--|{");

	private EntityRelationship(String s) {
		this.value = s;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
