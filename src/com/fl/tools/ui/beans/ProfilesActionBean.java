package com.fl.tools.ui.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.fl.tools.infr.domain.ProfileWrapper;

@ManagedBean
@RequestScoped
public class ProfilesActionBean {
	private ProfileWrapper profile;

	public ProfileWrapper getProfile() {
		return profile;
	}

	public void setProfile(ProfileWrapper profile) {
		this.profile = profile;
	}
}
