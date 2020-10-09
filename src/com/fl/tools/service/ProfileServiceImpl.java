package com.fl.tools.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.infr.dao.ProfileDao;
import com.fl.tools.infr.domain.Profile;

@Component
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	private ProfileDao profileDao;

	@Override
	public List<Profile> getProfiles() {
		return profileDao.getProfiles();
	}

	@Override
	public Profile getProfileDetails(String profileUUID) {
		return profileDao.getProfileDetails(profileUUID);
	}

}
