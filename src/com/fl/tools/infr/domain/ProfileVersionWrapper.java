package com.fl.tools.infr.domain;

public class ProfileVersionWrapper {
	private ProfileVersion theProfileVersion;
	private ComponentsMapView components;
	private DomainObjectsMapView domainObjects;

	public ProfileVersionWrapper(ProfileVersion theProfileVersion) {
		this.theProfileVersion = theProfileVersion;
	}

	public DomainObjectsMapView getDomainObjects() {
		return domainObjects;
	}

	public ComponentsMapView getComponents() {
		return components;
	}

	public void setComponents(ComponentsMapView components) {
		this.components = components;
	}

	public void setDomainObjects(DomainObjectsMapView domainObjects) {
		this.domainObjects = domainObjects;
	}

	public String getUUID() {
		return theProfileVersion.getUUID();
	}

	public String getVersionNumber() {
		return theProfileVersion.getVersionNumber();
	}

	public boolean isDefaultVersion() {
		return theProfileVersion.isDefaultVersion();
	}

	public String repoName() {
		return getUUID().substring(getUUID().lastIndexOf(':') + 1).toLowerCase();
	}
}
