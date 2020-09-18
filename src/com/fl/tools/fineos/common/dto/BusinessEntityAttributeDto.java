package com.fl.tools.fineos.common.dto;

import com.fl.tools.fineos.infr.domain.BusinessEntityAttribute;

public class BusinessEntityAttributeDto {
	private BusinessEntityAttribute attribute;
	private boolean fromParent;
	private String parentName;

	public BusinessEntityAttributeDto() {

	}

	public BusinessEntityAttributeDto(BusinessEntityAttribute attribute) {
		this(attribute, false, "");
	}

	public BusinessEntityAttributeDto(BusinessEntityAttribute attribute, boolean fromParent, String parentName) {
		super();
		this.attribute = attribute;
		this.fromParent = fromParent;
		this.parentName = parentName;
	}

	public BusinessEntityAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(BusinessEntityAttribute attribute) {
		this.attribute = attribute;
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
