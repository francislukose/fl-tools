package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class DomainValue {
	private String id;
	private String name;
	private boolean retired;
	private Set<Annotation> annotations = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<Annotation> annotations) {
		this.annotations = annotations;
	}
}
