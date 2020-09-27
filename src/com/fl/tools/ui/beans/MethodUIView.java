package com.fl.tools.ui.beans;

import com.fl.tools.infr.domain.MethodProxy;

public class MethodUIView {
	private MethodProxy method;
	private boolean fromParent;
	private String parentName;

	public MethodUIView(MethodProxy method) {
		this(method, false, "");

	}

	public MethodUIView(MethodProxy method, boolean extended, String parentName) {
		this.fromParent = extended;
		this.parentName = parentName;
		this.method = method;

	}

	public boolean isFromParent() {
		return fromParent;
	}

	public void setFromParent(boolean fromParent) {
		this.fromParent = fromParent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getViewStyle() {
		return isFromParent() ? "extended" : "";
	}
}
