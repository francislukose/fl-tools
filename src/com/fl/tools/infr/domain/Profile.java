package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class Profile {
	private String UUID;
	private String name;
	private boolean defaultProfile;
	private Set<ProfileVersion> profiles = new HashSet<>();

	public boolean isDefaultProfile() {
		return defaultProfile;
	}

	public void setDefaultProfile(boolean defaultProfile) {
		this.defaultProfile = defaultProfile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public Set<ProfileVersion> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<ProfileVersion> profiles) {
		this.profiles = profiles;
	}

}
