package com.fl.tools.infr.domain;

public class ProfileVersion {
	private String UUID;
	private String versionNumber;
	private boolean defaultVersion;

	public boolean isDefaultVersion() {
		return defaultVersion;
	}

	public void setDefaultVersion(boolean defaultVersion) {
		this.defaultVersion = defaultVersion;
	}

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
}
