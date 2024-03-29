package com.fl.tools.infr.domain;

import java.util.HashSet;
import java.util.Set;

public class Annotation {
	public static final String ANN_DATABASE_TABLE = "TableName";
	public static final String ANN_DATABASE_COL = "ColumnName";
	public static final String ANN_DATABASE_COL_TYPE = "ColumnType";
	public static final String ANN_DATABASE_COL_SIZE = "ColumnSize";
	public static final String ANN_DATABASE_COL_STERIOTYPE = "ColumnSteriotype";
	public static final String ANN_DOCUMENTATION = "Documentation";
	public static final String ANN_FOREIGN_KEY_NAME = "ForeignKeyName";
	public static final String ANN_MAPPED_BY = "MappedBy";

	private String name;
	private String value;
	private Set<Annotation> associatedAnnotations = new HashSet<>();

	public Annotation() {

	}

	public Annotation(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Annotation addAssociatedAnnotation(Annotation a) {
		associatedAnnotations.add(a);
		return this;
	}

	public void setAssociatedAnnotations(Set<Annotation> associatedAnnotations) {
		this.associatedAnnotations = associatedAnnotations;
	}

	public Set<Annotation> getAssociatedAnnotations() {
		return associatedAnnotations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Annotation other = (Annotation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Annotation [name=" + name + ", value=" + value + ", associatedAnnotations=" + associatedAnnotations
				+ "]";
	}

}
