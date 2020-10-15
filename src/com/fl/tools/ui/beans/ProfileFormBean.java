package com.fl.tools.ui.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@RequestScoped
public class ProfileFormBean {
	private String profileName;
	private String profileVersion;
	private Part components;
	private Part domainObjects;

	public Part getComponents() {
		return components;
	}

	public void setComponents(Part components) {
		this.components = components;
	}

	public Part getDomainObjects() {
		return domainObjects;
	}

	public void setDomainObjects(Part domainObjects) {
		this.domainObjects = domainObjects;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(String profileVersion) {
		this.profileVersion = profileVersion;
	}

	public void reset() {
		setComponents(null);
		setDomainObjects(null);
		setProfileName("");
		setProfileVersion("");
	}

}
