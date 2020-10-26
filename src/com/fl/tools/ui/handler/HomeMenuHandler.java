package com.fl.tools.ui.handler;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.ActionHandler;
import com.fl.tools.ui.beans.ProfileUIView;
import com.fl.tools.ui.beans.ProfilesActionBean;

@Component
@ManagedBean
public class HomeMenuHandler extends ActionHandler {
	@Autowired
	private ComponentService componentService;

	public String handleHomePageRequest() {
		return "/index";
	}

	public String handleClassesAndInterfacesComponentRequest() {
		return "/pages/components/components";
	}

	public String handleDomainObjectsRequest() {
		return "/pages/components/domainobjects";
	}

	public String handleDownloadRequest() {
		return "/pages/components/download";
	}

	public String handleManageProfilesRequest() {
		return "/pages/profile/manageprofile";
	}
}
