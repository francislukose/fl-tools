package com.fl.tools.service;

import com.fl.tools.infr.domain.Profile;

import java.util.List;

public interface ProfileService {
	public List<Profile> getProfiles();

	public Profile getProfileDetails(String profileUUID);

}
