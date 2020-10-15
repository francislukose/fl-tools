package com.fl.tools.common.dto;

public class ProfileLookupDto {
	private String profileUUID;
	private String profileVersionUUID;

	public ProfileLookupDto(String profileUUID, String profileVersionUUID) {
		super();
		this.profileUUID = profileUUID;
		this.profileVersionUUID = profileVersionUUID;
	}

	public String getProfileUUID() {
		return profileUUID;
	}

	public String getProfileVersionUUID() {
		return profileVersionUUID;
	}
}
