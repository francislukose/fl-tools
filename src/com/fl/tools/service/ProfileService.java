package com.fl.tools.service;

import com.fl.tools.common.dto.ProfileDto;
import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.infr.domain.ProfilesView;

public interface ProfileService {
	public ProfilesView getProfiles();

	public ProfileWrapper getProfileDetails(ProfileLookupDto profile);

	public String saveProfile(ProfileDto theProfile);

	public String removeProfile(ProfileLookupDto profile);

}
