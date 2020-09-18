package com.fl.tools.fineos.infr.domain;

public class BusinessEntityHierarchy {
	public String simpleName;
	public String packageName;
	public String fullName;
	public BusinessEntityHierarchy nextHierarchy;

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public BusinessEntityHierarchy getNextHierarchy() {
		return nextHierarchy;
	}

	public void setNextHierarchy(BusinessEntityHierarchy nextHierarchy) {
		this.nextHierarchy = nextHierarchy;
	}

}
