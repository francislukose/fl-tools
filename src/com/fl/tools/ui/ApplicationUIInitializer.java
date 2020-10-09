package com.fl.tools.ui;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.DomainObjectsUIView;

@Component
@ManagedBean
@SessionScoped
public class ApplicationUIInitializer {
	@Autowired
	private ComponentService componentService;
	
	@Autowired
	private ComponentUIView componentUIView;
	@Autowired
	private DomainObjectsUIView domainObjectsUIView;

	@PostConstruct
	public void init() {
		componentUIView.setComponents(componentService.getComponents(null));
		domainObjectsUIView.setDomainObjects(componentService.getDomainObjects(null));
	}
}
