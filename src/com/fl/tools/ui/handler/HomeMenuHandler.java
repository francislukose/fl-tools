package com.fl.tools.ui.handler;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.beans.ComponentUIView;

@Component
@ManagedBean
public class HomeMenuHandler {
	@Autowired
	private ComponentService componentService;
	@Autowired
	private ComponentUIView componentUIView;

	public String handleClassesAndInterfacesComponentRequest() {
		if (componentUIView.getComponents() == null) {
			componentUIView.setComponents(componentService.getComponents());
		}
		return "/pages/components/entities";
	}
}
