package com.fl.tools.ui;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "FToolUIConfiguration")
@ApplicationScoped
public class FToolUIConfiguration {
	@Value("${ftool.components.uml.UmlProviderURL}")
	private String plantUmlServer;

	public String getPlantUmlServer() {
		return plantUmlServer;
	}

	public void setPlantUmlServer(String plantUmlServer) {
		this.plantUmlServer = plantUmlServer;
	}
}
