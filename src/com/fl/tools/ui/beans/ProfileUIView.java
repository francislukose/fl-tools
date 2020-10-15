package com.fl.tools.ui.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.ProfileVersionWrapper;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.infr.domain.ProfilesView;

@ManagedBean
@SessionScoped
@Component
public class ProfileUIView {
	private ProfileView viewType = ProfileView.VIEW_PROFILE;
	private ProfilesView profiles;
	private ProfileWrapper selectedProfile;
	private ProfileVersionWrapper selectedProfileVersion;

	public int getSize() {
		if (!profiles.hasProfiles()) {
			return 0;
		}
		return profiles.getProfiles().size();
	}

	public ProfileWrapper getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(ProfileWrapper selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

	public ProfileVersionWrapper getSelectedProfileVersion() {
		return selectedProfileVersion;
	}

	public void setSelectedProfileVersion(ProfileVersionWrapper selectedProfileVersion) {
		this.selectedProfileVersion = selectedProfileVersion;
	}

	public ProfilesView getProfiles() {
		return profiles;
	}

	public void setProfiles(ProfilesView profiles) {
		this.profiles = profiles;
	}

	public void setViewType(ProfileView viewType) {
		this.viewType = viewType;
	}

	public ProfileView getViewType() {
		return viewType;
	}

	public boolean isNewProfileView() {
		return viewType == ProfileView.NEW_PROFILE;
	}

	public boolean isViewProfileView() {
		return viewType == ProfileView.VIEW_PROFILE;
	}
}
