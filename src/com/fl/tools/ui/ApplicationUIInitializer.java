package com.fl.tools.ui;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.infr.domain.ProfileVersionWrapper;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.service.ComponentService;
import com.fl.tools.service.ProfileService;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.DomainObjectsUIView;
import com.fl.tools.ui.beans.ProfileUIView;

@Component
@ManagedBean
@SessionScoped
public class ApplicationUIInitializer {
	@Autowired
	private ProfileService profileService;

	@Autowired
	private ProfileUIView profileUIView;
	@Autowired
	private ComponentUIView componentUIView;
	@Autowired
	private DomainObjectsUIView domainObjectsUIView;

	@PostConstruct
	public void init() {
		profileUIView.setProfiles(profileService.getProfiles());

		if (profileUIView.getProfiles().hasProfiles()) {
			ProfileWrapper selectedProfile = profileService
					.getProfileDetails(new ProfileLookupDto(profileUIView.getProfiles().getDefaultProfile().getUUID(),
							profileUIView.getProfiles().getDefaultProfile().defaultVersion().getUUID()));
			profileUIView.setSelectedProfile(selectedProfile);
			if (selectedProfile.getProfiles().iterator().hasNext()) {
				ProfileVersionWrapper version = selectedProfile.getProfiles().iterator().next();
				profileUIView.setSelectedProfileVersion(version);
				componentUIView.setComponents(version.getComponents());
				domainObjectsUIView.setDomainObjects(version.getDomainObjects());
			}
		}
	}

	public void flush() {
		init();
	}
	public void reload() {
		profileUIView.setProfiles(profileService.getProfiles());
		if (profileUIView.getSelectedProfile() == null && profileUIView.getProfiles().hasProfiles()) {
			ProfileWrapper selectedProfile = profileService
					.getProfileDetails(new ProfileLookupDto(profileUIView.getProfiles().getDefaultProfile().getUUID(),
							profileUIView.getProfiles().getDefaultProfile().defaultVersion().getUUID()));
			profileUIView.setSelectedProfile(selectedProfile);
			if (selectedProfile.getProfiles().iterator().hasNext()) {
				ProfileVersionWrapper version = selectedProfile.getProfiles().iterator().next();
				profileUIView.setSelectedProfileVersion(version);
				componentUIView.setComponents(version.getComponents());
				domainObjectsUIView.setDomainObjects(version.getDomainObjects());
			}
		}
	}

	public void resetProfileSelection(ProfileLookupDto lookup) {
		ProfileWrapper selectedProfile = profileService.getProfileDetails(lookup);
		if (selectedProfile != null) {
			profileUIView.setSelectedProfile(selectedProfile);
			if (selectedProfile.getProfiles().iterator().hasNext()) {
				ProfileVersionWrapper version = selectedProfile.getProfiles().iterator().next();
				profileUIView.setSelectedProfileVersion(version);
				componentUIView.setComponents(version.getComponents());
				domainObjectsUIView.setDomainObjects(version.getDomainObjects());
			}
		}
	}

}
