package com.fl.tools.infr.dao;

import java.util.List;

import com.fl.tools.infr.domain.Profile;

public interface ProfileDao {
	public List<Profile> getProfiles();

	public Profile getProfileDetails(String profileUUID);
}
