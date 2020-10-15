package com.fl.tools.common.dto;

public class ProfileDto {
	private String profileName;
	private String profileVersion;
	private byte[] components;
	private byte[] domainObjects;

	public String getProfileName() {
		return profileName;
	}

	public ProfileDto setProfileName(String profileName) {
		this.profileName = profileName;
		return this;
	}

	public String getProfileVersion() {
		return profileVersion;
	}

	public ProfileDto setProfileVersion(String profileVersion) {
		this.profileVersion = profileVersion;
		return this;
	}

	public byte[] getComponents() {
		return components;
	}

	public ProfileDto setComponents(byte[] components) {
		this.components = components;
		return this;
	}

	public byte[] getDomainObjects() {
		return domainObjects;
	}

	public ProfileDto setDomainObjects(byte[] domainObjects) {
		this.domainObjects = domainObjects;
		return this;
	}

}
