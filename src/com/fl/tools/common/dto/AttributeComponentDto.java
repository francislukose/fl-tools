package com.fl.tools.common.dto;

import com.fl.tools.infr.domain.AttributeProxy;
import com.fl.tools.infr.domain.ComponentProxy;

public class AttributeComponentDto {
	private AttributeProxy attributeProxy;
	private ComponentProxy typeProxy;

	public AttributeComponentDto(AttributeProxy attributeProxy, ComponentProxy typeProxy) {
		super();
		this.attributeProxy = attributeProxy;
		this.typeProxy = typeProxy;
	}

	public AttributeProxy getAttributeProxy() {
		return attributeProxy;
	}

	public ComponentProxy getTypeProxy() {
		return typeProxy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeProxy == null) ? 0 : attributeProxy.getName().hashCode());
		result = prime * result + ((typeProxy == null) ? 0 : typeProxy.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeComponentDto other = (AttributeComponentDto) obj;
		if (attributeProxy == null) {
			if (other.attributeProxy != null)
				return false;
		} else if (!attributeProxy.getName().equals(other.attributeProxy.getName()))
			return false;
		if (typeProxy == null) {
			if (other.typeProxy != null)
				return false;
		} else if (!typeProxy.getName().equals(other.typeProxy.getName()))
			return false;
		return true;
	}

}
