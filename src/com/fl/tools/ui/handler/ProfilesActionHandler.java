package com.fl.tools.ui.handler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.ProfileDto;
import com.fl.tools.common.dto.ProfileLookupDto;
import com.fl.tools.common.utils.FileUtil;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.service.ProfileService;
import com.fl.tools.ui.ActionHandler;
import com.fl.tools.ui.beans.ProfileFormBean;
import com.fl.tools.ui.beans.ProfileUIView;
import com.fl.tools.ui.beans.ProfileView;
import com.fl.tools.ui.beans.ProfilesActionBean;

@Component
@RequestScoped
public class ProfilesActionHandler extends ActionHandler {
	@Autowired
	private ProfileService profileService;

	protected void setProfileViewSelection(String uuid) {
		ProfileUIView profileView = getManagedBean("profileUIView");

		for (ProfileWrapper p : profileView.getProfiles().getProfiles()) {
			if (p.getUUID().equalsIgnoreCase(uuid)) {
				ProfilesActionBean actionBean = getManagedBean("profilesActionBean");
				actionBean.setProfile(p);
				break;
			}
		}
		profileView.setViewType(ProfileView.VIEW_PROFILE);
	}

	public void handleNewProfileRequest(AjaxBehaviorEvent evt) {
		ProfileUIView profileView = getManagedBean("profileUIView");
		profileView.setViewType(ProfileView.NEW_PROFILE);
	}

	public void handleRemoveProfileVersionRequest(AjaxBehaviorEvent evt) {
		System.out.println("handleRemoveProfileVersionRequest");
		String versionUUID = (String) evt.getComponent().getAttributes().get("versionUUID");
		String profileUUID = (String) evt.getComponent().getAttributes().get("profileUUID");

		profileService.removeProfile(new ProfileLookupDto(profileUUID, versionUUID));
		reloadSessionInitializer();

		setProfileViewSelection(profileUUID);
	}

	public void handleSwitchProfileRequest(AjaxBehaviorEvent evt) {
		String versionUUID = (String) evt.getComponent().getAttributes().get("versionUUID");
		String profileUUID = (String) evt.getComponent().getAttributes().get("profileUUID");

		reloadSessionInitializer();

		setProfileViewSelection(profileUUID);
	}

	public void handleMakeDefaultProfileVersionRequest(AjaxBehaviorEvent evt) {
		String versionUUID = (String) evt.getComponent().getAttributes().get("versionUUID");
		String profileUUID = (String) evt.getComponent().getAttributes().get("profileUUID");

		profileService.removeProfile(new ProfileLookupDto(profileUUID, versionUUID));
		reloadSessionInitializer();

		setProfileViewSelection(profileUUID);
	}

	public void handleRemoveProfileRequest(AjaxBehaviorEvent evt) {
		String profileUUID = (String) evt.getComponent().getAttributes().get("profileUUID");

		profileService.removeProfile(new ProfileLookupDto(profileUUID, null));
		flushSession();
	}

	public void handleViewSelectedProfileRequest(AjaxBehaviorEvent evt) {
		String uuid = (String) evt.getComponent().getAttributes().get("uuid");
		setProfileViewSelection(uuid);
	}

	public String handleSaveProfileRequest() {
		ProfileUIView profileView = getManagedBean("profileUIView");
		ProfileFormBean profileFormBean = getManagedBean("profileFormBean");
		try {
			ProfileDto profileDto = new ProfileDto().setProfileName(profileFormBean.getProfileName())
					.setProfileVersion(profileFormBean.getProfileVersion());
			if (profileFormBean.getComponents() != null) {
				profileDto.setComponents(FileUtil.toBytes(profileFormBean.getComponents().getInputStream()));
			}
			if (profileFormBean.getDomainObjects() != null) {
				profileDto.setDomainObjects(FileUtil.toBytes(profileFormBean.getDomainObjects().getInputStream()));
			}

			profileService.saveProfile(profileDto);
			reloadSessionInitializer();

			profileView.setViewType(ProfileView.VIEW_PROFILE);
			profileFormBean.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/pages/profile/manageprofile";
	}
}
