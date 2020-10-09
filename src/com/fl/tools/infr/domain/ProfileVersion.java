package com.fl.tools.infr.domain;

public class ProfileVersion {
	private String UUID;
	private String versionNumber;
	private String componentConfig;
	private String domainObjectConfig;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getComponentConfig() {
		return componentConfig;
	}

	public void setComponentConfig(String componentConfig) {
		this.componentConfig = componentConfig;
	}

	public String getDomainObjectConfig() {
		return domainObjectConfig;
	}

	public void setDomainObjectConfig(String domainObjectConfig) {
		this.domainObjectConfig = domainObjectConfig;
	}

}
