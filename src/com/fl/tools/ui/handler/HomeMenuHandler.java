package com.fl.tools.ui.handler;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.DomainObjectsUIView;

@Component
@ManagedBean
public class HomeMenuHandler {
	@Autowired
	private ComponentService componentService;
	@Autowired
	private ComponentUIView componentUIView;
	@Autowired
	private DomainObjectsUIView domainObjectsUIView;

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
