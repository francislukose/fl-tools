package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class Method {
	private String name;
	private String UUID;
	private String returnType;
	private String returnTypeUUID;

	private Set<Modifier> modifiers = new HashSet<>();
	private Set<Steriotype> steriotypes = new HashSet<>();
	private Set<Annotation> annotations = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnTypeUUID() {
		return returnTypeUUID;
	}

	public void setReturnTypeUUID(String returnTypeUUID) {
		this.returnTypeUUID = returnTypeUUID;
	}

	public Set<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(Set<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public Set<Steriotype> getSteriotypes() {
		return steriotypes;
	}

	public void setSteriotypes(Set<Steriotype> steriotypes) {
		this.steriotypes = steriotypes;
	}

	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<Annotation> annotations) {
		this.annotations = annotations;
	}
}
