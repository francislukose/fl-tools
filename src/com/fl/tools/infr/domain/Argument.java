package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class Argument {
	private String name;
	private String argType;
	private String argTypeUUID;

	private Set<Modifier> modifiers = new HashSet<>();
	private Set<Steriotype> steriotypes = new HashSet<>();
	private Set<Annotation> annotations = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArgType() {
		return argType;
	}

	public void setArgType(String argType) {
		this.argType = argType;
	}

	public String getArgTypeUUID() {
		return argTypeUUID;
	}

	public void setArgTypeUUID(String argTypeUUID) {
		this.argTypeUUID = argTypeUUID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Argument other = (Argument) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
