package com.fl.tools.infr.domain.v2;

import java.util.HashSet;
import java.util.Set;

public class Attribute {
	private String name;
	private String type;
	private String UUID;
	private String defaultValue;
	private Set<Modifier> modifiers = new HashSet<>();
	private Set<Steriotype> steriotypes = new HashSet<>();
	private Set<Annotation> annotations = new HashSet<>();

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getUUID() {
		return UUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
