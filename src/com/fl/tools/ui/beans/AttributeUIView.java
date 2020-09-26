package com.fl.tools.ui.beans;

import com.fl.tools.infr.domain.AttributeProxy;

public class AttributeUIView {
	private AttributeProxy attribute;
	private boolean fromParent;
	private String parentName;

	public AttributeUIView(AttributeProxy attr) {
		this(attr, false, "");
	}

	public AttributeUIView(AttributeProxy attr, boolean extended, String parent) {
		this.attribute = attr;
		this.fromParent = extended;
		this.parentName = parent;
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

	public AttributeProxy getAttribute() {
		return attribute;
	}

	public void setAttribute(AttributeProxy attribute) {
		this.attribute = attribute;
	}

	public String getViewStyle() {
		return isFromParent() ? "extended" : "";
	}
}
