package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class ProfileWrapper {
	private Profile theProfile;
	private Set<ProfileVersionWrapper> profiles = new HashSet<>();

	public ProfileWrapper() {

	}

	public ProfileWrapper(Profile theProfile) {

		this.theProfile = theProfile;
		theProfile.getProfiles().forEach((e) -> profiles.add(new ProfileVersionWrapper(e)));
	}

	public String getName() {
		return theProfile.getName();
	}

	public String getUUID() {
		return theProfile.getUUID();
	}

	public Set<ProfileVersionWrapper> getProfiles() {
		return profiles;
	}

	public boolean isDefaultProfile() {
		return theProfile.isDefaultProfile();
	}

	public String repoName() {
		return getUUID().substring(4).toLowerCase();
	}

	public ProfileVersionWrapper defaultVersion() {
		ProfileVersionWrapper defaultVersion = null;
		if (profiles.size() > 0) {
			defaultVersion = profiles.iterator().next();
			for (ProfileVersionWrapper v : profiles) {
				if (v.isDefaultVersion()) {
					defaultVersion = v;
					break;
				}
			}
		}

		return defaultVersion;
	}
}
