package com.fl.tools.infr.dao;

import java.util.Collection;

import com.fl.tools.common.dto.ProfileDto;
import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.infr.domain.Profile;

public interface ProfileDao {
	public Collection<Profile> getProfiles();

	public Profile getProfileDetails(ProfileLookupDto profile);

	public String saveProfile(ProfileDto theProfile);

	public String removeProfile(ProfileLookupDto profile);
}
