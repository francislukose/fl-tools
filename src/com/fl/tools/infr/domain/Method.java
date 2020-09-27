package com.fl.tools.infr.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Method {
	private String name;
	private String UUID;
	private String returnTypeUUID;
	private List<MethodParam> parameters = new ArrayList<>();
	private Set<Modifier> modifiers = new HashSet<>();
	private Set<Steriotype> steriotypes = new HashSet<>();
	private Set<Annotation> annotations = new HashSet<>();

	public List<MethodParam> getParameters() {
		return parameters;
	}

	public void setParameters(List<MethodParam> parameters) {
		this.parameters = parameters;
	}

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
