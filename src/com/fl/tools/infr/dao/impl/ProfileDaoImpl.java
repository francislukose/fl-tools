package com.fl.tools.infr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.common.dto.ProfileDto;
import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.common.utils.FileUtil;
import com.fl.tools.infr.config.InfrConfiguration;
import com.fl.tools.infr.dao.ProfileDao;
import com.fl.tools.infr.domain.DomainObject;
import com.fl.tools.infr.domain.Profile;
import com.fl.tools.infr.domain.ProfileVersion;
import com.fl.tools.infr.domain.ProfileVersionWrapper;
import com.fl.tools.infr.domain.ProfileWrapper;

@Component
public class ProfileDaoImpl implements ProfileDao {
	@Autowired
	private InfrConfiguration configuration;

	private ProfileJsonDataSource profileDataSource = new ProfileJsonDataSource();

	@PostConstruct
	public void initialize() {
		profileDataSource.initialize();
	}

	@Override
	public Collection<Profile> getProfiles() {
		return profileDataSource.getProfiles();
	}

	@Override
	public Profile getProfileDetails(ProfileLookupDto profile) {
		return profileDataSource.getProfileDetails(profile.getProfileUUID());
	}

	@Override
	public String saveProfile(ProfileDto theProfile) {
		return profileDataSource.saveProfile(theProfile).getUUID();
	}

	@Override
	public String removeProfile(ProfileLookupDto profile) {
		return profileDataSource.remove(profile);
	}

	class ProfileJsonDataSource {
		private Map<String, Profile> profiles = new HashMap<>();

		public void initialize() {
			init();
		}

		protected void init() {
			try {
				profiles.clear();
				ObjectMapper objectMapper = new ObjectMapper();
				Set<Profile> theProfiles = objectMapper.readValue(new FileInputStream(
						configuration.getRootDir() + File.separator + "data" + File.separator + "ftools-profiles.json"),
						new TypeReference<Set<Profile>>() {
						});
				theProfiles.forEach((e) -> profiles.put(e.getUUID(), e));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		protected void updateRepository() {
			ObjectMapper om = new ObjectMapper();
			try {
				String jsonText = om.writerWithDefaultPrettyPrinter().writeValueAsString(profiles.values());
				FileUtil.saveToDisk(jsonText.getBytes(), "ftools-profiles.json",
						configuration.getRootDir() + File.separator + "data");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			init();

		}

		public Collection<Profile> getProfiles() {
			return profiles.values();
		}

		public Profile getProfileDetails(String profileUUID) {
			return profiles.get(profileUUID);
		}

		public Profile saveProfile(ProfileDto theProfile) {
			Profile aProfile = new Profile();
			ProfileVersion aVersion = new ProfileVersion();

			aProfile.setName(theProfile.getProfileName());
			aProfile.setUUID("PID:" + theProfile.getProfileName().replaceAll(" ", "").replaceAll(":", ""));
			aVersion.setVersionNumber(theProfile.getProfileVersion());
			aVersion.setUUID(
					aProfile.getUUID() + ":" + theProfile.getProfileVersion().replaceAll(" ", "").replaceAll(":", ""));

			aProfile.getProfiles().add(aVersion);

			ProfileWrapper profileWrapper = new ProfileWrapper(aProfile);
			String repoLocation = profileWrapper.repoName() + File.separator
					+ profileWrapper.getProfiles().iterator().next().repoName();

			FileUtil.saveToDisk(theProfile.getComponents(), "ftools-components.json",
					configuration.getRootDir() + File.separator + "data" + File.separator + repoLocation);
			FileUtil.saveToDisk(theProfile.getDomainObjects(), "ftools-domain-objects.json",
					configuration.getRootDir() + File.separator + "data" + File.separator + repoLocation);

			if (profiles.containsKey(aProfile.getUUID())) {
				profiles.get(aProfile.getUUID()).getProfiles().add(aVersion);
			} else {
				profiles.put(aProfile.getUUID(), aProfile);
			}

			updateRepository();

			return aProfile;
		}

		public String remove(ProfileLookupDto theProfile) {
			if (profiles.containsKey(theProfile.getProfileUUID())) {
				Profile profile = profiles.get(theProfile.getProfileUUID());
				ProfileWrapper profileWrapper = new ProfileWrapper(profile);
				if (theProfile.getProfileVersionUUID() != null) {
					ProfileVersionWrapper theVersion = profileWrapper
							.getProfileVersion(theProfile.getProfileVersionUUID());
					if (theVersion != null) {
						FileUtil.removeDir(configuration.getRootDir() + File.separator + "data" + File.separator
								+ profileWrapper.repoName() + File.separator + theVersion.repoName());
						profile.getProfiles().remove(theVersion.getActual());
					}
				} else {
					FileUtil.removeDir(configuration.getRootDir() + File.separator + "data" + File.separator
							+ profileWrapper.repoName());
					profiles.remove(profile.getUUID());
				}
				updateRepository();
			}
			return theProfile.getProfileUUID();
		}
	}

}
