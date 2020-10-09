package com.fl.tools.infr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.fl.tools.infr.config.InfrConfiguration;
import com.fl.tools.infr.dao.ProfileDao;
import com.fl.tools.infr.domain.Profile;

@Component
public class ProfileDaoImpl implements ProfileDao {
	@Autowired
	private InfrConfiguration configuration;

	private ProfileJsonDataSource profileDataSource = new ProfileJsonDataSource();

	@Override
	public List<Profile> getProfiles() {
		return profileDataSource.getProfiles();
	}

	@Override
	public Profile getProfileDetails(String profileUUID) {
		return profileDataSource.getProfileDetails(profileUUID);
	}

	class ProfileJsonDataSource {
		protected void init() {
			
		}
		public List<Profile> getProfiles() {
			return null;
		}

		public Profile getProfileDetails(String profileUUID) {
			return null;
		}

	}

}
