package com.fl.tools.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.ProfileDto;
import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.dao.DomainObjectDao;
import com.fl.tools.infr.dao.ProfileDao;
import com.fl.tools.infr.domain.Profile;
import com.fl.tools.infr.domain.ProfileVersionWrapper;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.infr.domain.ProfilesView;

@Component
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private ComponentDao componentDao;
	@Autowired
	private DomainObjectDao domainObjectDao;

	@Override
	public ProfilesView getProfiles() {
		ProfilesView profilesView = new ProfilesView();
		Collection<ProfileWrapper> wrappers = new ArrayList<>();

		profileDao.getProfiles().forEach((e) -> wrappers.add(new ProfileWrapper(e)));

		profilesView.setProfiles(wrappers);
		if (profilesView.getDefaultProfile() == null && profilesView.hasProfiles()) {
			profilesView.setDefaultProfile(profilesView.getProfiles().iterator().next());
		}

		return profilesView;
	}

	@Override
	public ProfileWrapper getProfileDetails(ProfileLookupDto profile) {
		ProfileWrapper wrapper = null;
		Profile theProfile = profileDao.getProfileDetails(profile);
		if (theProfile != null) {
			wrapper = new ProfileWrapper(theProfile);
			if (profile.getProfileVersionUUID() != null && profile.getProfileVersionUUID().length() > 0) {
				ProfileVersionWrapper version = null;
				for (ProfileVersionWrapper v : wrapper.getProfiles()) {
					if (v.getUUID().equalsIgnoreCase(profile.getProfileVersionUUID())) {
						version = v;
						break;
					}
				}

				wrapper.getProfiles().clear();
				if (version != null) {
					version.setComponents(componentDao.getComponents(version.getUUID()));
					version.setDomainObjects(domainObjectDao.getDomainObjects(version.getUUID()));
					wrapper.getProfiles().add(version);
				}
			}
		}

		return wrapper;
	}

	@Override
	public String saveProfile(ProfileDto theProfile) {
		return profileDao.saveProfile(theProfile);
	}

	@Override
	public String removeProfile(ProfileLookupDto profile) {
		return profileDao.removeProfile(profile);
	}

}
