package com.fl.tools.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.DomainObjectsUIView;

@Component
@ApplicationScoped
public class Application {
	@Autowired
	private ComponentService componentService;
	@Autowired
	private ComponentUIView componentUIView;
	@Autowired
	private DomainObjectsUIView domainObjectsUIView;

	@PostConstruct
	public void init() {
		componentUIView.setComponents(componentService.getComponents());
		domainObjectsUIView.setDomainObjects(componentService.getDomainObjects());
	}
}
