package com.fl.tools.infr.domain;

import java.util.Set;

public class DomainObjectProxy {
	private DomainObject actualObject;

	public DomainObjectProxy(DomainObject actualObject) {
		this.actualObject = actualObject;
	}

	public Set<DomainValue> getDomainValues() {
		return actualObject.getDomainValues();
	}

	public int getDomainValueCount() {
		return actualObject.getDomainValues().size();
	}

	public String getUUID() {
		return actualObject.getUUID();
	}

	public String getName() {
		return actualObject.getName();
	}

	public String getPackageName() {
		return actualObject.getPackageName();
	}

	public String getDomainId() {
		return actualObject.getDomainId();
	}

	public boolean isDeprecated() {
		return actualObject.isDeprecated();
	}

	public String getDisplayName() {
		return actualObject.getDisplayName();
	}

	public Set<Annotation> getAnnotations() {
		return actualObject.getAnnotations();
	}

	public String getDescription() {
		for (Annotation a : actualObject.getAnnotations()) {
			if (a.getName().equalsIgnoreCase(Annotation.ANN_DOCUMENTATION)) {
				return a.getValue();
			}
		}
		return "";
	}

}
