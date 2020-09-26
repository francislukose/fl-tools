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
		if (componentUIView.getComponents() == null) {
			componentUIView.setComponents(componentService.getComponents());
		}
		return "/pages/components/components";
	}

	public String handleDomainObjectsRequest() {
		if (domainObjectsUIView.getDomainObjects() == null) {
			domainObjectsUIView.setDomainObjects(componentService.getDomainObjects());
		}
		return "/pages/components/domainobjects";
	}

}
