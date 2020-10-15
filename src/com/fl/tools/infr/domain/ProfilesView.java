package com.fl.tools.infr.domain;

import java.util.Collection;

public class ProfilesView {
	private Collection<ProfileWrapper> profiles;
	private ProfileWrapper defaultProfile;

	public ProfileWrapper getDefaultProfile() {
		return defaultProfile;
	}

	public Collection<ProfileWrapper> getProfiles() {
		return profiles;
	}

	public boolean hasProfiles() {
		return profiles != null && profiles.size() > 0;
	}

	public void setDefaultProfile(ProfileWrapper defaultProfile) {
		this.defaultProfile = defaultProfile;
	}

	public void setProfiles(Collection<ProfileWrapper> profiles) {
		this.profiles = profiles;
		if (profiles != null) {
			for (ProfileWrapper p : profiles) {
				if (p.isDefaultProfile()) {
					setDefaultProfile(p);
					break;
				}
			}
		}
	}
}
