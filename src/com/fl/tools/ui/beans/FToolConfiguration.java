package com.fl.tools.ui.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "FToolConfiguration")
@ApplicationScoped
public class FToolConfiguration {
	private String plantUmlServer = "http://localhost:18080/plantuml/svg/";

	public String getPlantUmlServer() {
		return plantUmlServer;
	}

	public void setPlantUmlServer(String plantUmlServer) {
		this.plantUmlServer = plantUmlServer;
	}
}
