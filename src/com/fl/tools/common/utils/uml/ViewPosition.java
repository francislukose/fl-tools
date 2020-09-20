package com.fl.tools.common.utils.uml;

public enum ViewPosition {
	DOWN("-down-"), UP("-up-");

	private ViewPosition(String n) {
		this.label = n;
	}

	public String label;
}
