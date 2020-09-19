package com.fl.tools.infr.domain;

import java.util.List;

public class TypeList {
	private String simpleName;
	private String fullName;
	private String packageName;
	private String domainId;
	private String description;
	private boolean deprecated;
	private String displayName;
	private List<TypeCode> domainObjects;

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<TypeCode> getDomainObjects() {
		return domainObjects;
	}

	public void setDomainObjects(List<TypeCode> domainObjects) {
		this.domainObjects = domainObjects;
	}

}
