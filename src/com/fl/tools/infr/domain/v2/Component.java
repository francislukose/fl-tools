package com.fl.tools.infr.domain.v2;

import java.util.HashSet;
import java.util.Set;

public class Component {
	private String UUID;
	private String name;
	private String packageName;
	private Set<Attribute> attributes = new HashSet<>();
	private Set<Method> methods = new HashSet<>();
	private String parent;
	private Set<String> children = new HashSet<>();

	private Set<Modifier> modifiers = new HashSet<>();
	private Set<Steriotype> steriotypes = new HashSet<>();
	private Set<Annotation> annotations = new HashSet<>();

	public Set<Method> getMethods() {
		return methods;
	}

	public void setMethods(Set<Method> methods) {
		this.methods = methods;
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getUUID() {
		return packageName + ":" + name;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Set<String> getChildren() {
		return children;
	}

	public void setChildren(Set<String> children) {
		this.children = children;
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
	public String toString() {
		return "Component [UUID=" + getUUID() + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getUUID() == null) ? 0 : getUUID().hashCode());
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
		Component other = (Component) obj;
		if (getUUID() == null) {
			if (other.getUUID() != null)
				return false;
		} else if (!getUUID().equals(other.getUUID()))
			return false;
		return true;
	}

}
