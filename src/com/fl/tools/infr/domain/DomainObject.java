package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class DomainObject {
	private String UUID;
	private String name;
	private String packageName;
	private String domainId;
	private boolean deprecated;
	private String displayName;
	private Set<Annotation> annotations = new HashSet<>();
	private Set<DomainValue> domainValues = new HashSet<>();
	private Set<DomainValue> domainObjects;

	public Set<DomainValue> getDomainObjects() {
		return domainObjects;
	}

	public void setDomainObjects(Set<DomainValue> domainObjects) {
		this.domainObjects = domainObjects;
	}

	public void setDomainValues(Set<DomainValue> domainValues) {
		this.domainValues = domainValues;
	}

	public Set<DomainValue> getDomainValues() {
		return domainValues;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<Annotation> annotations) {
		this.annotations = annotations;
	}

}
